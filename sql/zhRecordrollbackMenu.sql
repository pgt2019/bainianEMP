-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('识别记录', '3', '1', '/console/zhRecordrollback', 'C', '0', 'console:zhRecordrollback:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '识别记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('识别记录查询', @parentId, '1',  '#',  'F', '0', 'console:zhRecordrollback:list',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('识别记录新增', @parentId, '2',  '#',  'F', '0', 'console:zhRecordrollback:add',          '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('识别记录修改', @parentId, '3',  '#',  'F', '0', 'console:zhRecordrollback:edit',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('识别记录删除', @parentId, '4',  '#',  'F', '0', 'console:zhRecordrollback:remove',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');
