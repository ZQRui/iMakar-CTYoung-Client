﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;
using System.Security.Cryptography;
using System.Xml;
using System.Diagnostics;
using System.IO;

namespace iMakar_CTYoung_Client
{
    abstract class CTYoung
    {
        private const string strUrlBaseHaiNan = "http://124.225.128.205:10001";
        private const string strUrlUuidHaiNan = "/xyportal/?wlanuserip=10.20.2.141&wlanacip=202.100.206.253";
        private const string strUA = "China Telecom Client";
        protected string username;
        protected string passwd;
        private string ip;
        protected string host;
        private string uuid;
        protected string acname;
        protected string loginURL;
        protected string logoffURL;
        protected string en_passwd;
        protected HttpClient httpClient = new HttpClient();
        protected bool isLogin;
        MD5CryptoServiceProvider md5csp = new MD5CryptoServiceProvider();

        public bool IsLogin { get { return isLogin; } }

        public static CTYoung Init(string location, string ip, string username, string passwd, string uuid = "")
        {
            if (location == "辽宁")
            {
                return new CTYoungLN(ip, username, passwd,uuid);
            }
            else if (location == "海南")
            {
                return new CTYoungHN(ip, username, passwd,uuid);
            }
            else
            {
                throw new NotSupportedException("不支持的地区");
            }

        }

        public CTYoung(string ip, string username, string passwd,string uuid)
        {
            if (!Is_ip_valid(ip)) throw new Exception("ip错误");
            this.Ip = ip;
            this.username = username;
            //if(md5csp.ComputeHash()= "DD0682614B9FB737")
            this.passwd = passwd;
            this.en_passwd = Encrypt_passwd(passwd);
            httpClient.DefaultRequestHeaders.Add("User-Agent", strUA);
            Uuid = uuid;
        }
        abstract protected string Location { get; }

        static public bool Is_ip_valid(string ip)
        {
            ip = ip.Trim(' ');
            string[] arr = ip.Split('.');
            if (arr.Length != 4) return false;
            //if (arr[0] != "100" && arr[0] != "10") return false;
            for (int i = 0; i < 4; i++)
            {
                int num = 0;
                try
                {
                    num = Convert.ToInt16(arr[i]);
                }
                catch
                {
                    return false;
                }

                if (num < 0 || num > 255)
                {
                    return false;
                }
            }
            return true;
        }

        abstract protected string Key { get; }

        protected string Encrypt_passwd(string passwd)
        {
            //TODO encrypt passwd
            string key = Key;
            byte[] mdk = md5csp.ComputeHash(Encoding.Default.GetBytes(key));
            byte[] encrypted;
            using (Aes myAes = Aes.Create())
            {
                byte[] iv = new byte[16];
                var encryptor = myAes.CreateEncryptor(mdk, iv);
                using (MemoryStream msEncrypt = new MemoryStream())
                {
                    using (CryptoStream csEncrypt = new CryptoStream(msEncrypt, encryptor, CryptoStreamMode.Write))
                    {
                        using (StreamWriter swEncrypt = new StreamWriter(csEncrypt))
                        {
                            swEncrypt.Write(passwd);
                        }
                        encrypted = msEncrypt.ToArray();
                    }
                }
            }
            return BitConverter.ToString(encrypted).Replace("-", "");
        }

        abstract protected string ShowLoginUrl { get; }
        abstract protected string ShowLoginPostString { get; }
        public string Uuid { get => uuid; protected set => uuid = value; }
        public string Ip { get => ip; protected set => ip = value; }

        protected virtual bool DoRequestUuid()
        {
            Uri uriUuid = new Uri(ShowLoginUrl);
            HttpContent contentUuid = new StringContent(ShowLoginPostString, Encoding.UTF8, "application/x-www-form-urlencoded");
            HttpResponseMessage m = httpClient.PostAsync(uriUuid, contentUuid).Result;
            string responseText = m.Content.ReadAsStringAsync().Result;
            Debug.WriteLine(responseText);
            XmlDocument xmlDocument = new XmlDocument();
            try
            {
                xmlDocument.LoadXml(responseText);
            }
            catch (System.Xml.XmlException)
            {
                return false;
            }
            var redirect = xmlDocument.SelectSingleNode("/WISPAccessGatewayParam/Redirect");
            string msgType = redirect.SelectSingleNode("MessageType").InnerText;
            this.loginURL = redirect.SelectSingleNode("LoginURL").InnerText;
            this.Uuid = redirect.SelectSingleNode("Uuid").InnerText;
            string responseCode = redirect.SelectSingleNode("ResponseCode").InnerText;

            Trace.TraceInformation(redirect.ToString());
            Debug.WriteLine($"msgType: {msgType}");
            Debug.WriteLine($"loginURL: {loginURL}");
            Debug.WriteLine($"uuid : {Uuid}");
            Debug.WriteLine($"responseCode : {responseCode}");

            if (responseCode != "0")
            {
                return false;
            }

            return true;
        }

        protected virtual bool DoRequestLogin()
        {
            //请求登陆

            Uri uriLogin = new Uri(this.loginURL);
            Dictionary<string, string> dictLogin = new Dictionary<string, string>();
            dictLogin["uuid"] = this.Uuid;
            dictLogin["userip"] = this.Ip;
            dictLogin["username"] = this.username;
            dictLogin["password"] = this.en_passwd;
            if (acname!=null)
            {
                dictLogin["acname"] = this.acname;
            }
            HttpContent contLogin = new FormUrlEncodedContent(dictLogin);
            //HttpContent contentLogin = new StringContent($"",Encoding.UTF8, "application/x-www-form-urlencoded");
            HttpResponseMessage m = httpClient.PostAsync(uriLogin, contLogin).Result;
            string responseText = m.Content.ReadAsStringAsync().Result;
            Debug.WriteLine(responseText);
            XmlDocument xmlDocument = new XmlDocument();
            try
            {
                xmlDocument.LoadXml(responseText);
            }
            catch (System.Xml.XmlException)
            {
                return false;
            }
            var reply = xmlDocument.SelectSingleNode("/WISPAccessGatewayParam/AuthenticationReply");
            string msgType = reply.SelectSingleNode("MessageType").InnerText;
            string responseCode = reply.SelectSingleNode("ResponseCode").InnerText;
            this.logoffURL = reply.SelectSingleNode("LogoffURL").InnerText;
            Trace.TraceInformation(reply.ToString());

            Debug.WriteLine($"msgType: {msgType}");
            Debug.WriteLine($"logoffURL: {logoffURL}");
            Debug.WriteLine($"responseCode : {responseCode}");

            if (responseCode != "200")
            {
                return false;
            }

            return true;
        }

        protected virtual bool DoRequestLogout()
        {
            //请求注销

            Uri uriLogin = new Uri(this.logoffURL);
            Dictionary<string, string> dictLogout = new Dictionary<string, string>();
            dictLogout["uuid"] = this.Uuid;
            dictLogout["userip"] = this.Ip;
            if (this.acname != null)
            {
                dictLogout["acname"] = acname;
            }
            HttpContent cntLogout = new FormUrlEncodedContent(dictLogout);
            //HttpContent contentLogin = new StringContent($"",Encoding.UTF8, "application/x-www-form-urlencoded");
            HttpResponseMessage m = httpClient.PostAsync(uriLogin, cntLogout).Result;
            string responseText = m.Content.ReadAsStringAsync().Result;
            Debug.WriteLine(responseText);
            XmlDocument xmlDocument = new XmlDocument();
            try
            {
                xmlDocument.LoadXml(responseText);
            }
            catch (System.Xml.XmlException)
            {
                return false;
            }
            var reply = xmlDocument.SelectSingleNode("/LogoffReply");
            string msgType = reply.SelectSingleNode("MessageType").InnerText;
            string responseCode = reply.SelectSingleNode("ResponseCode").InnerText;
            string date = reply.SelectSingleNode("Date").InnerText;

            Debug.WriteLine($"msgType: {msgType}");
            Debug.WriteLine($"responseCode : {responseCode}");
            Debug.WriteLine($"date: {date}");

            if (responseCode == "255")
            {
                return false;
            }

            return true;
        }


        public bool Login()
        {
            //获取uuid loginURL 

            if (!DoRequestUuid()) return false;
            if (!DoRequestLogin()) return false;
            return true;

        }
        public bool Logout()
        {
            //TODO
            if (!DoRequestLogout()) return false;
            return true;
        }



    }
    sealed class CTYoungLN : CTYoung
    {
        public const string location = "辽宁";
        private const string showLoginUrl = "http://219.148.205.34:8090/showlogin.do";
        private const string key = "LntoLL03";

        protected override string Key { get { return key; } }

        public CTYoungLN(string ip, string username, string passwd,string uuid) : base(ip, username, passwd,uuid)
        {

        }
        protected override string Location { get { return location; } }
        protected override string ShowLoginUrl
        {
            get { return showLoginUrl; }
        }

        protected override string ShowLoginPostString
        {
            get { return "{\"wlanuserip\" : \"" + this.Ip + "\" }"; }
        }
    }
    sealed class CTYoungHN : CTYoung
    {
        public CTYoungHN(string ip, string username, string passwd,string uuid) : base(ip, username, passwd,uuid)
        {
            acname = hnacname;
        }
        public const string location = "海南";
        private const string showLoginUrl = "http://124.225.128.205:10001/xyportal/";
        const string hnacname = "202.100.206.253";
        private const string key = "leil";

        protected override string Location
        {
            get
            {
                return location;
            }
        }
        protected override string ShowLoginUrl
        {
            get { return showLoginUrl + $"?wlanuserip={Ip}wlanacip={acname}"; }
        }

        protected override string ShowLoginPostString
        {
            get { return $"{{\"wlanuserip\" : \"{this.Ip}\"}}"; }
        }

        protected override string Key { get { return key; } }

        protected override bool DoRequestUuid()
        {
            Uri uriUuid = new Uri(showLoginUrl);
            HttpResponseMessage m = httpClient.GetAsync(uriUuid).Result;
            string responseText = m.Content.ReadAsStringAsync().Result;
            Debug.WriteLine(responseText);
            XmlDocument xmlDocument = new XmlDocument();
            try
            {
                xmlDocument.LoadXml(responseText);
            }
            catch (System.Xml.XmlException)
            {
                return false;
            }

            var redirect = xmlDocument.SelectSingleNode("/WISPAccessGatewayParam/Redirect");
            string msgType = redirect.SelectSingleNode("MessageType").InnerText;
            this.loginURL = redirect.SelectSingleNode("LoginURL").InnerText;
            this.Uuid = redirect.SelectSingleNode("Uuid").InnerText;
            string responseCode = redirect.SelectSingleNode("ResponseCode").InnerText;
            Trace.TraceInformation(redirect.ToString());
            Debug.WriteLine($"msgType: {msgType}");
            Debug.WriteLine($"loginURL: {loginURL}");
            Debug.WriteLine($"uuid : {Uuid}");
            Debug.WriteLine($"responseCode : {responseCode}");

            if (responseCode != "0")
            {
                return false;
            }

            return true;
        }
    }
}

