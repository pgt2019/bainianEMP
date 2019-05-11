-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台api  人员', '3', '1', '/console/zhUser', 'C', '0', 'console:zhUser:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '振汇开放平台api  人员菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台api  人员查询', @parentId, '1',  '#',  'F', '0', 'console:zhUser:list',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台api  人员新增', @parentId, '2',  '#',  'F', '0', 'console:zhUser:add',          '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台api  人员修改', @parentId, '3',  '#',  'F', '0', 'console:zhUser:edit',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('振汇开放平台api  人员删除', @parentId, '4',  '#',  'F', '0', 'console:zhUser:remove',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');
