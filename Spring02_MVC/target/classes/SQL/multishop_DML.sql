-- 3/14

insert into member(userid, name, pwd, hp1,hp2,hp3,
post, addr1, addr2, indate, mileage, mstate)
values('hong123', 'ȫ�浿', '1234', '010','1111','2222','12345','���� ������ ��ȭ��', 
'1����', sysdate, 1000, 0);
commit;

select * from member;

insert into upcategory(upcg_code, upcg_name)
values(1, '������ǰ');
insert into upcategory(upcg_code, upcg_name)
values(2, '��Ȱ��ǰ');
insert into upcategory(upcg_code, upcg_name)
values(3, '�Ƿ�');

select * from upcategory;
commit;

desc downcategory;
insert into downcategory(upcg_code, downcg_code, downcg_name)
values(1, 1, '��Ʈ��');
insert into downcategory(upcg_code, downcg_code, downcg_name)
values(1, 2, '�����');
insert into downcategory(upcg_code, downcg_code, downcg_name)
values(2, 3, '�ֹ漼��');
insert into downcategory(upcg_code, downcg_code, downcg_name)
values(2, 4, '����');
insert into downcategory(upcg_code, downcg_code, downcg_name)
values(3, 5, '�����Ƿ�');
insert into downcategory(upcg_code, downcg_code, downcg_name)
values(3, 6, '�����Ƿ�');

select * from downcategory;
commit;


