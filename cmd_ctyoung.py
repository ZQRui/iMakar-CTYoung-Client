# -*- coding: gbk -*-
"""

"""
#   @Time:  2017/8/30 14:54
#   @Author:still_night@163.com
#   @File:  cmd_ctyoung.py
from core import SinglenetSession, User
import sys

default_encoding = sys.getdefaultencoding()


def cmd_login():
    print("�뽫�豸����ct-young,\n��������Ҫ��½ct-young���豸��ip,�û���,���벢���س�ȷ��\n")
    userip = str(raw_input(">>ip: ").decode(default_encoding).strip())
    username = str(raw_input(">>�˺�: ").decode(default_encoding).strip())
    password = str(raw_input("����:  ").decode(default_encoding).strip())
    print("��ȷ�� \nip:{0}\n�û���{1}\n����:{2}\n  ".format(userip, username, password))
    yes = raw_input("��y����,���������ȡ��").decode(default_encoding).strip().upper()
    if yes == 'Y':
        print("��ʼ��½")
        session = SinglenetSession(userip=userip, user=User(username, password))
        if session.login():
            print("��½�ɹ� \n user:{}\nip:{}\nuuid: {} \n�����Ʊ���ip �� uuid����ע��\n".format(session.user.username, session.userip,session.uuid))
        else:
            print("��½ʧ��:����{}\n".format(session.login_response_code))


def cmd_logout():
    print("�������½ʱ��ip��uuid������ע��\n")
    userip = str(raw_input(">>ip: ").decode(default_encoding).strip())
    uuid = str(raw_input(">>uuid:  ").decode(default_encoding).strip())
    print("����ע�� \nip:{} \nuuid:{}".format(userip, uuid))
    session = SinglenetSession(uuid=uuid, userip=userip)
    if session.logout():
        print("ע���ɹ� \n ip:{}\n".format(userip))
    else:
        print("ע��ʧ�� : ip:{}  {} ".format(userip, session.get_code_msg(session.logout_response_code)))


def cmd_run():
    print("""
    ҽ��У԰��CT-Young��½��

    1.��½
    2.ע��
    ��ѡ����Ĳ���Ȼ�󰴻س�������
    """)
    choice = str(raw_input("").decode(default_encoding).strip())
    if choice == "1":
        cmd_login()
    elif choice == "2":
        cmd_logout()
    else:
        print("��������,�����˳�")
    raw_input("�����������....")


if __name__ == '__main__':
    # s=SinglenetSession()
    # s.post("http://www.baidu.com")
    # u=User("edu189","154914")
    # print u.en_password
    # edu18940248503
    # 154914
    # session = SinglenetSession(userip="100.76.168.214", user=User("edu18940248503", "154914"))
    # session.login()
    try:
        cmd_run()
    except Exception as e:

        print("{}: {}".format(e.__class__.__name__,e))
        raw_input()
        raise

