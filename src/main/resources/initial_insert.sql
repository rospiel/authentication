insert into `permission`(`name`, `description`)
values('READ', 'Only search');

insert into `permission`(`name`, `description`)
values('WRITE', 'Only write');

insert into `group`(`name`)
values('API_BANK_SLIP');

insert into `group`(`name`)
values('API_BANK_SLIP_ADMIN');

insert into `group_permission`(`group_id`, `permission_id`)
values(1, 1);

insert into `group_permission`(`group_id`, `permission_id`)
values(2, 1);

insert into `group_permission`(`group_id`, `permission_id`)
values(2, 2);
