 -- ########################### 面向对象大作业 - 数据库表初始化脚本 ###########################
-- 适配环境：华为云RDS MySQL 8.x
-- 字符集：utf8mb4（兼容emoji/特殊字符，华为云推荐），校对规则：utf8mb4_unicode_ci（统一排序）
-- 注意：执行前需先在华为云RDS创建数据库（如 oop_project），并使用该数据库
USE oop_project;

-- 1. 用户表（面向对象“用户”实体，封装用户属性与行为关联）
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    user_id BIGINT AUTO_INCREMENT COMMENT '用户ID（主键，自增）',
    username VARCHAR(50) NOT NULL COMMENT '用户名（唯一，面向对象属性）',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储，面向对象封装特性）',
    real_name VARCHAR(20) NOT NULL COMMENT '真实姓名',
    email VARCHAR(50) DEFAULT '' COMMENT '邮箱',
    phone VARCHAR(20) DEFAULT '' COMMENT '手机号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常 0-禁用（面向对象状态属性）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (user_id),
    UNIQUE INDEX idx_username (username) -- 用户名唯一索引，提升查询效率
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表（面向对象核心实体）';

-- 2. 项目表（面向对象“项目”实体，封装项目核心属性）
DROP TABLE IF EXISTS sys_project;
CREATE TABLE sys_project (
    project_id BIGINT AUTO_INCREMENT COMMENT '项目ID（主键，自增）',
    project_name VARCHAR(100) NOT NULL COMMENT '项目名称（面向对象属性）',
    project_desc TEXT COMMENT '项目描述（面向对象属性）',
    creator_id BIGINT NOT NULL COMMENT '创建人ID（关联用户表，面向对象关联特性）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (project_id),
    INDEX idx_creator_id (creator_id), -- 关联用户索引
    FOREIGN KEY (creator_id) REFERENCES sys_user(user_id) ON DELETE RESTRICT -- 外键约束（面向对象关联）
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目信息表（面向对象核心实体）';

-- 3. 模块表（面向对象“模块”实体，项目的组成单元，体现组合模式）
DROP TABLE IF EXISTS sys_module;
CREATE TABLE sys_module (
    module_id BIGINT AUTO_INCREMENT COMMENT '模块ID（主键，自增）',
    module_name VARCHAR(100) NOT NULL COMMENT '模块名称（面向对象属性）',
    module_desc TEXT COMMENT '模块描述（面向对象属性）',
    project_id BIGINT NOT NULL COMMENT '所属项目ID（关联项目表，组合模式）',
    creator_id BIGINT NOT NULL COMMENT '创建人ID（关联用户表）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (module_id),
    INDEX idx_project_id (project_id), -- 关联项目索引
    INDEX idx_creator_id (creator_id), -- 关联用户索引
    FOREIGN KEY (project_id) REFERENCES sys_project(project_id) ON DELETE CASCADE, -- 项目删除则模块删除（组合特性）
    FOREIGN KEY (creator_id) REFERENCES sys_user(user_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目模块表（面向对象组合模式体现）';

-- 4. 操作日志表（记录用户对项目/模块的操作，面向对象行为追溯）
DROP TABLE IF EXISTS sys_oper_log;
CREATE TABLE sys_oper_log (
    log_id BIGINT AUTO_INCREMENT COMMENT '日志ID（主键，自增）',
    user_id BIGINT NOT NULL COMMENT '操作用户ID（关联用户表）',
    oper_type VARCHAR(20) NOT NULL COMMENT '操作类型：ADD-新增 MODIFY-修改 DELETE-删除 QUERY-查询',
    oper_table VARCHAR(50) NOT NULL COMMENT '操作表名：sys_project/sys_module/sys_user',
    oper_id BIGINT NOT NULL COMMENT '操作数据ID（关联对应表主键）',
    oper_content TEXT COMMENT '操作内容（面向对象行为描述）',
    oper_ip VARCHAR(50) DEFAULT '' COMMENT '操作IP',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (log_id),
    INDEX idx_user_id (user_id), -- 关联用户索引
    INDEX idx_oper_time (create_time) -- 按时间查询索引
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志表（面向对象行为追溯）';

-- ########################### 华为云RDS适配说明 ###########################
-- 1. 执行方式：登录华为云RDS控制台 → 数据库管理 → SQL编辑器 → 执行该脚本；或通过JDBC/Navicat连接后执行
-- 2. 权限要求：使用华为云RDS创建的高权限账号（如root）执行，确保有建表/外键/索引权限
-- 3. 性能优化：华为云RDS支持索引优化，以上表已添加核心索引，生产环境可根据查询场景补充
-- 4. 存储引擎：强制使用InnoDB（华为云RDS默认，支持事务/外键，符合面向对象业务事务特性）
