DROP DATABASE IF EXISTS INTERIOR_DESIGN_DB;
CREATE DATABASE INTERIOR_DESIGN_DB
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE INTERIOR_DESIGN_DB;

-- Create AUTH_PROVIDER table
CREATE TABLE AUTH_PROVIDER (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    REMARK_EN VARCHAR(255),
    REMARK VARCHAR(255),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data AUTH_PROVIDER
INSERT INTO AUTH_PROVIDER (`NAME`, REMARK_EN, REMARK)
VALUES 
    ('USERNAME', 'Username Authentication', 'Xác thực bằng tên đăng nhập'),
    ('EMAIL', 'Email Authentication', 'Xác thực bằng địa chỉ email'),
    ('PHONE', 'Phone Authentication', 'Xác thực bằng số điện thoại'),
    ('SSO_GOOGLE', 'Google SSO', 'Đăng nhập một lần qua Google'),
    ('SSO_FACEBOOK', 'Facebook SSO', 'Đăng nhập một lần qua Facebook'),
    ('LDAP', 'LDAP Authentication', 'Xác thực qua hệ thống LDAP'),
    ('API_KEY', 'API Key Authentication', 'Xác thực bằng khoá API');

-- Modify USER table
DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    USER_NAME VARCHAR(255) UNIQUE NOT NULL,
    PASSWORD_HASH VARCHAR(255) NOT NULL,
    FIRST_NAME VARCHAR(100),
    LAST_NAME VARCHAR(100),
    PHONE_NUMBER VARCHAR(20),
    EMAIL VARCHAR(255),
    AVATAR_URL VARCHAR(2048),
    AVATAR_BASE64 LONGTEXT,
    IS_ADMIN BOOLEAN DEFAULT FALSE,
    IS_ENABLE BOOLEAN DEFAULT TRUE,
    IS_NON_EXPIRED BOOLEAN DEFAULT TRUE,
    IS_CREDENTIALS_NON_EXPIRED BOOLEAN DEFAULT TRUE,
    IS_NON_LOCKED BOOLEAN DEFAULT TRUE,
    AUTH_PROVIDER_ID BIGINT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (AUTH_PROVIDER_ID) REFERENCES AUTH_PROVIDER(ID) ON DELETE RESTRICT
);

-- Create CATEGORY table
DROP TABLE IF EXISTS CATEGORY;
CREATE TABLE CATEGORY (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    SLUG VARCHAR(255) NOT NULL UNIQUE,
    NAME VARCHAR(255) NOT NULL,
    DESCRIPTION_CATEGORY TEXT,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data AUTH_PROVIDER
INSERT INTO CATEGORY (SLUG, `NAME`, DESCRIPTION_CATEGORY)
VALUES 
    ('Tat_ca', 'Tất cả', 'Tất cả'),
    ('Can_ho', 'Căn hộ', 'Căn hộ'),
    ('Biet_thu', 'Biệt thự', 'Biệt thự'),
    ('Van_phong', 'Văn phòng', 'Văn phòng'),
    ('Nha_hang', 'Nhà hàng', 'Nhà hàng');

-- Create BRAND table
DROP TABLE IF EXISTS BRAND;
CREATE TABLE BRAND (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    SLUG VARCHAR(255) NOT NULL UNIQUE,
    NAME VARCHAR(255) NOT NULL,
    DESCRIPTION_BRAND TEXT,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create IMAGE table
DROP TABLE IF EXISTS IMAGE;
CREATE TABLE IMAGE (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    IMAGE_BASE64 LONGTEXT,
    IMAGE_URL VARCHAR(2048),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CHECK (IMAGE_BASE64 IS NOT NULL OR IMAGE_URL IS NOT NULL)
);

INSERT INTO `IMAGE` (IMAGE_BASE64, IMAGE_URL) VALUES 
('Image base64 01', 'Imaage base64 01'), 
('Image base64 02', 'Imaage base64 02'), 
('Image base64 03', 'Imaage base64 03'), 
('Image base64 04', 'Imaage base64 04'), 
('Image base64 05', 'Imaage base64 05'), 
('Image base64 06', 'Imaage base64 06'),
('Image base64 07', 'Imaage base64 07'), 
('Image base64 08', 'Imaage base64 08'), 
('Image base64 09', 'Imaage base64 09'), 
('Image base64 10', 'Imaage base64 10');

-- Create PRODUCT_STATUS table
DROP TABLE IF EXISTS PRODUCT_STATUS;
CREATE TABLE PRODUCT_STATUS (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    REMARK_EN VARCHAR(255),
    REMARK VARCHAR(255),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Modify PRODUCT table
DROP TABLE IF EXISTS PRODUCT;
CREATE TABLE PRODUCT (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    SLUG VARCHAR(255) NOT NULL UNIQUE,
    NAME TEXT NOT NULL,
    DESCRIPTION_PRODUCT TEXT,
    MATERIAL VARCHAR(255),
    PRICE DECIMAL(15,2),
    LENGHT DECIMAL(10,2),
    WIDTH DECIMAL(10,2),
    HEIGHT DECIMAL(10,2),
    WEIGHT DECIMAL(10,2),
    COLOR VARCHAR(50),
    CATEGORY_ID BIGINT,
    BRAND_ID BIGINT,
    PRODUCT_STATUS_ID BIGINT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(ID) ON DELETE SET NULL,
    FOREIGN KEY (BRAND_ID) REFERENCES BRAND(ID) ON DELETE SET NULL,
    FOREIGN KEY (PRODUCT_STATUS_ID) REFERENCES PRODUCT_STATUS(ID) ON DELETE RESTRICT
);

-- Create PROJECT_STATUS table
DROP TABLE IF EXISTS PROJECT_STATUS;
CREATE TABLE PROJECT_STATUS (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    REMARK_EN VARCHAR(255),
    REMARK VARCHAR(255),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO PROJECT_STATUS (NAME, REMARK_EN, REMARK) VALUES 
('Đang triển khai', 'In Progress', 'Dự án đang trong quá trình thi công'),
('Hoàn thành', 'Completed', 'Dự án đã hoàn thành'),
('Tạm dừng', 'Paused', 'Dự án tạm ngừng triển khai'),
('Hủy bỏ', 'Cancelled', 'Dự án bị hủy bỏ'),
('Đang phê duyệt', 'Under Approval', 'Chờ duyệt kế hoạch');

-- Modify PROJECT table
DROP TABLE IF EXISTS PROJECT;
CREATE TABLE PROJECT (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    SLUG VARCHAR(255) NOT NULL UNIQUE,
    TITLE VARCHAR(255),
    INVESTOR VARCHAR(255),
		ACREAGE VARCHAR(100),
		DESCRIPTION TEXT,
		DESIGNER VARCHAR(255),
    LOCATION VARCHAR(255),
    FACTORY VARCHAR(255),
    HOTLINE VARCHAR(105),
    EMAIL VARCHAR(105),
    Website VARCHAR(105),
    PROJECT_STATUS_ID BIGINT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (PROJECT_STATUS_ID) REFERENCES PROJECT_STATUS(ID) ON DELETE RESTRICT
);

INSERT INTO PROJECT (SLUG, TITLE, INVESTOR, ACREAGE, DESCRIPTION, DESIGNER, LOCATION, FACTORY, HOTLINE, EMAIL, Website, PROJECT_STATUS_ID)VALUES 
('eco-park-01', 'Eco Park Riverside', 'Tập đoàn EcoLand', '12000 m²', 'Khu đô thị sinh thái ven sông với môi trường sống trong lành.', 'Green Arch Co.', 'Quận 9, TP.HCM', 'Xưởng số 1', '18001001', 'info@ecoland.vn', 'www.ecopark.vn', 1),
('green-garden-02', 'Green Garden Villas', 'Công ty CP Xanh', '8000 m²', 'Khu biệt thự xanh cao cấp giữa lòng thành phố.', 'CityBuild Ltd.', 'Quận 2, TP.HCM', 'Xưởng số 2', '18001002', 'sales@xanh.vn', 'www.greengarden.vn', 2),
('sky-tower-03', 'Sky Tower', 'Công ty CP Xây dựng Đông Á', '15000 m²', 'Tòa nhà căn hộ cao tầng, tiện ích hiện đại.', 'Skyline Group', 'Hà Nội', 'Xưởng 3', '18001003', 'contact@dongaxd.vn', 'www.skytower.vn', 1),
('sun-residence-04', 'Sun Residence', 'Sun Group', '13000 m²', 'Khu căn hộ cao cấp với view biển.', 'SunDesign Co.', 'Đà Nẵng', 'Xưởng miền Trung', '18001004', 'sun@group.vn', 'www.sunres.vn', 2),
('city-complex-05', 'City Complex', 'City Group', '20000 m²', 'Tổ hợp thương mại – văn phòng – căn hộ.', 'CityArch', 'TP. HCM', 'Xưởng chính', '18001005', 'info@city.vn', 'www.citycomplex.vn', 1),
('lake-view-06', 'Lake View Town', 'Lakeside Corp', '9500 m²', 'Dự án nhà phố ven hồ.', 'FreshDesign Ltd.', 'Hồ Tây, Hà Nội', 'Xưởng Bắc', '18001006', 'support@lakeside.vn', 'www.lakeview.vn', 1),
('urban-life-07', 'Urban Life', 'Công ty Địa ốc Mới', '7000 m²', 'Chung cư giá rẻ cho người thu nhập thấp.', 'Affordable Arch', 'Bình Dương', 'Xưởng số 4', '18001007', 'urban@real.vn', 'www.urbanlife.vn', 3),
('resort-hill-08', 'Resort Hill Villas', 'Công ty nghỉ dưỡng Việt', '11000 m²', 'Biệt thự nghỉ dưỡng trên đồi view biển.', 'Luxury Home Co.', 'Nha Trang', 'Xưởng miền Trung', '18001008', 'contact@resorthill.vn', 'www.resorthill.vn', 1),
('river-home-09', 'River Home', 'HomeLand Co.', '6000 m²', 'Căn hộ view sông với thiết kế thông minh.', 'ModernArch', 'Cần Thơ', 'Xưởng miền Tây', '18001009', 'info@river.vn', 'www.riverhome.vn', 1),
('garden-city-10', 'Garden City', 'Green World JSC', '17000 m²', 'Khu đô thị vườn thông minh.', 'NatureDesign Ltd.', 'Hải Phòng', 'Xưởng phía Bắc', '18001010', 'contact@green.vn', 'www.gardencity.vn', 2),
('central-park-11', 'Central Park Office', 'TP Corp', '14000 m²', 'Văn phòng hạng A trung tâm.', 'TP Architects', 'Q.1, TP.HCM', 'Xưởng 1', '18001011', 'office@tp.vn', 'www.centralpark.vn', 1),
('tech-hub-12', 'Tech Innovation Hub', 'Innovation Inc.', '9000 m²', 'Trung tâm R&D công nghệ cao.', 'TechBuilds', 'Đà Nẵng', 'Xưởng CN', '18001012', 'hi@techhub.vn', 'www.techhub.vn', 1),
('harmony-13', 'Harmony Town', 'Công ty CP Phát triển Đô Thị', '10500 m²', 'Đô thị thân thiện với môi trường.', 'EcoUrban Ltd.', 'Thái Nguyên', 'Xưởng Bắc', '18001013', 'support@harmony.vn', 'www.harmony.vn', 2),
('peaceful-home-14', 'Peaceful Home', 'Công ty Nhà Việt', '7500 m²', 'Căn hộ cho người cao tuổi nghỉ dưỡng.', 'SeniorDesign', 'Long An', 'Xưởng miền Tây', '18001014', 'info@nhaviet.vn', 'www.peacehome.vn', 4),
('infinity-resort-15', 'Infinity Resort', 'Infinity Travel Group', '18000 m²', 'Khu nghỉ dưỡng 5 sao ven biển.', 'LuxuryResort Ltd.', 'Phú Quốc', 'Xưởng miền Nam', '18001015', 'resort@infinity.vn', 'www.infinityresort.vn', 2),
('blue-ocean-16', 'Blue Ocean Apartments', 'Công ty Đại Dương Xanh', '8500 m²', 'Chung cư sát biển với tiện ích đầy đủ.', 'OceanDesigns', 'Vũng Tàu', 'Xưởng biển', '18001016', 'info@blue.vn', 'www.blueocean.vn', 3),
('smart-city-17', 'Smart City', 'SmartTech Group', '22000 m²', 'Thành phố thông minh hiện đại.', 'Smart Urbanist', 'TP. Thủ Đức', 'Xưởng chính', '18001017', 'smart@city.vn', 'www.smartcity.vn', 5),
('sunny-valley-18', 'Sunny Valley', 'Sunny Holdings', '9300 m²', 'Nhà phố liền kề mang phong cách châu Âu.', 'EuroArch', 'Biên Hòa', 'Xưởng ĐN', '18001018', 'contact@sunny.vn', 'www.sunnyvalley.vn', 2),
('eco-homes-19', 'Eco Homes', 'GreenLife JSC', '6600 m²', 'Căn hộ sinh thái thông minh.', 'EcoHome Design', 'Củ Chi, TP.HCM', 'Xưởng sinh thái', '18001019', 'eco@homes.vn', 'www.ecohomes.vn', 1),
('delta-tower-20', 'Delta Tower', 'Delta Construction', '12500 m²', 'Tòa nhà văn phòng và trung tâm thương mại.', 'DeltaArch', 'Cầu Giấy, Hà Nội', 'Xưởng Hà Nội', '18001020', 'delta@build.vn', 'www.deltatower.vn', 5);


-- Create IMAGE_ATTRIBUTE table
DROP TABLE IF EXISTS IMAGE_ATTRIBUTE;
CREATE TABLE IMAGE_ATTRIBUTE (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    IMAGE_ID BIGINT,
    ATTR_ID BIGINT NOT NULL,
    TYPE INT(2) DEFAULT 1 CHECK (TYPE IN (1, 2)),
    IS_PRIMARY BOOLEAN DEFAULT FALSE,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (IMAGE_ID) REFERENCES IMAGE(ID) ON DELETE CASCADE
);

-- Create CATEGORY_ATTRIBUTE table
DROP TABLE IF EXISTS CATEGORY_ATTRIBUTE;
CREATE TABLE CATEGORY_ATTRIBUTE (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    CATEGORY_ID BIGINT NOT NULL,
    ATTR_ID BIGINT NOT NULL,
    TYPE INT(2) DEFAULT 1 CHECK (TYPE IN (0, 1)),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(ID) ON DELETE CASCADE
);

-- Create SECTION_TYPE table
DROP TABLE IF EXISTS SECTION_TYPE;
CREATE TABLE SECTION_TYPE (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    REMARK_EN VARCHAR(255),
    REMARK VARCHAR(255),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data SECTION_TYPE
INSERT INTO SECTION_TYPE (NAME, REMARK_EN, REMARK)
VALUES 
    ('SLIDE', 'Slide', 'Slide'),
    ('BANNER', 'Banner', 'Banner'),
    ('IMAGE', 'Image', 'Image');

-- Create SECTION table
DROP TABLE IF EXISTS SECTION;
CREATE TABLE SECTION (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    CODE VARCHAR(50) NOT NULL UNIQUE,
    TITLE VARCHAR(255),
    SECTION_TYPE_ID BIGINT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (SECTION_TYPE_ID) REFERENCES SECTION_TYPE(ID) ON DELETE RESTRICT
);

-- Create SECTION_ITEM table
DROP TABLE IF EXISTS SECTION_ITEM;
CREATE TABLE SECTION_ITEM (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    `ORDER` INT NOT NULL DEFAULT 1,
    TITLE VARCHAR(255),
    DESCRIPTION_ITEM TEXT,
    SECTION_ID BIGINT NOT NULL,
    IMAGE_ID BIGINT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (SECTION_ID) REFERENCES SECTION(ID) ON DELETE CASCADE,
    FOREIGN KEY (IMAGE_ID) REFERENCES IMAGE(ID) ON DELETE RESTRICT
);

-- Create TESTIMONIAL table
DROP TABLE IF EXISTS TESTIMONIAL;
CREATE TABLE TESTIMONIAL (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    CONTENT TEXT,
    RATING INT CHECK (RATING >= 1 AND RATING <= 5),
    PROJECT_ID BIGINT NOT NULL,
    IMAGE_ID BIGINT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT(ID) ON DELETE CASCADE,
    FOREIGN KEY (IMAGE_ID) REFERENCES IMAGE(ID) ON DELETE RESTRICT
);

-- Insert sample TESTIMONIAL data
INSERT INTO TESTIMONIAL (NAME, CONTENT, RATING, PROJECT_ID, IMAGE_ID) VALUES 
('Anh Bình', 'Giá hợp lý, chất lượng tốt, đội ngũ thi công thân thiện.', 5, 1, 1),
('Chị Hương', 'Thiết kế sang trọng, phù hợp với không gian sống.', 4, 2, 2),
('Anh Duy', 'Mình được người quen giới thiệu cho bên này. Đánh giá chất lượng khá tốt, giá thành ổn, bàn giao đúng tiến độ.', 5, 3, 3),
('Chị Lan', 'Tư vấn nhiệt tình, chuyên nghiệp.', 5, 4, 4),
('Anh Tuấn', 'Sản phẩm đẹp, chất lượng cao.', 4, 5, 5),
('Chị Mai', 'Dịch vụ chăm sóc khách hàng tốt.', 5, 6, 6),
('Anh Hoàng', 'Đội ngũ thi công chuyên nghiệp.', 4, 7, 7),
('Chị Thảo', 'Rất hài lòng với kết quả cuối cùng.', 5, 8, 8),
('Anh Đức', 'Giá cả phải chăng, chất lượng tốt.', 4, 9, 9),
('Chị Ngọc', 'Thiết kế hiện đại, phù hợp xu hướng.', 5, 10, 10);

-- Create SPONSOR table
DROP TABLE IF EXISTS SPONSOR;
CREATE TABLE SPONSOR (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    `ORDER` INT NOT NULL DEFAULT 1,
    IS_ACTIVE BOOLEAN DEFAULT TRUE,
    IMAGE_ID BIGINT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (IMAGE_ID) REFERENCES IMAGE(ID) ON DELETE RESTRICT
);

-- Insert sample SPONSOR data
INSERT INTO SPONSOR (NAME, `ORDER`, IS_ACTIVE, IMAGE_ID) VALUES 
('An Cường', 1, true, 1),
('Malloca', 2, true, 2),
('Häfele', 3, true, 3),
('Hettich', 4, true, 4),
('Blum', 5, true, 5),
('Caesar', 6, true, 6),
('Dulux', 7, true, 7),
('Jotun', 8, true, 8),
('TOTO', 9, true, 9),
('Kohler', 10, true, 10);

-- Create QUOTE_TYPE table
DROP TABLE IF EXISTS QUOTE_TYPE;
CREATE TABLE QUOTE_TYPE (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    DESCRIPTION TEXT,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data for QUOTE_TYPE
INSERT INTO QUOTE_TYPE (NAME, DESCRIPTION)
VALUES 
    ('Thiết kế nội thất', 'Thiết kế nội thất'),
    ('Thi công nội thất', 'Thi công nội thất');

-- Create QUOTE table
DROP TABLE IF EXISTS QUOTE;
CREATE TABLE QUOTE (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    -- SLUG VARCHAR(255) NOT NULL UNIQUE,
    TITLE VARCHAR(255) NOT NULL,
    SUBTITLE VARCHAR(255),
    QUOTE_TYPE_ID BIGINT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (QUOTE_TYPE_ID) REFERENCES QUOTE_TYPE(ID) ON DELETE RESTRICT
);

-- Insert sample QUOTE data
INSERT INTO QUOTE (TITLE, SUBTITLE, QUOTE_TYPE_ID) VALUES 
('Báo giá tủ bếp căn hộ 70m2', 'Chung cư Vinhomes', 1),
('Báo giá tủ áo phòng master', 'Biệt thự Phú Mỹ Hưng', 2),
('Báo giá tủ bếp biệt thự', 'Biệt thự Thảo Điền', 1),
('Báo giá tủ áo walk-in', 'Penthouse Landmark 81', 2),
('Báo giá tủ bếp căn hộ 100m2', 'Chung cư Masteri', 1),
('Báo giá tủ áo 4 cánh', 'Căn hộ Sunrise City', 2),
('Báo giá tủ bếp chữ L', 'Nhà phố Thủ Đức', 1),
('Báo giá tủ áo âm tường', 'Căn hộ Empire City', 2),
('Báo giá tủ bếp island', 'Biệt thự Quận 2', 1),
('Báo giá tủ áo kết hợp bàn trang điểm', 'Căn hộ Feliz En Vista', 2);

-- Create QUOTE_ITEM table to handle the array of items
DROP TABLE IF EXISTS QUOTE_ITEM;
CREATE TABLE QUOTE_ITEM (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    QUOTE_ID BIGINT NOT NULL,
    CONTENT VARCHAR(255) NOT NULL,
    PRICE VARCHAR(100) NOT NULL,
    -- QUANTITY INT NOT NULL DEFAULT 1 CHECK (QUANTITY >= 1),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (QUOTE_ID) REFERENCES QUOTE(ID) ON DELETE CASCADE
);

-- Insert sample QUOTE_ITEM data
INSERT INTO QUOTE_ITEM (QUOTE_ID, CONTENT, PRICE) VALUES 
(1, 'Tủ bếp trên', '15,000,000 VNĐ'),
(1, 'Tủ bếp dưới', '25,000,000 VNĐ'),
(2, 'Tủ quần áo 4 cánh', '35,000,000 VNĐ'),
(2, 'Kệ giày', '8,000,000 VNĐ'),
(3, 'Tủ bếp cao cấp', '45,000,000 VNĐ'),
(3, 'Đảo bếp', '30,000,000 VNĐ'),
(4, 'Tủ áo walk-in', '55,000,000 VNĐ'),
(4, 'Kệ phụ kiện', '12,000,000 VNĐ'),
(5, 'Tủ bếp module', '28,000,000 VNĐ'),
(5, 'Phụ kiện tủ bếp', '7,000,000 VNĐ');

-- Create PROJECT_INFO table to handle the array of items
DROP TABLE IF EXISTS PROJECT_INFO;
CREATE TABLE PROJECT_INFO (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    TYPICAL_PROJECT_TITLE VARCHAR(255) NOT NULL,
    TYPICAL_PROJECT_SUB_TITLE TEXT NOT NULL,
    FEATURED_PROJECT_TITLE VARCHAR(255) NOT NULL,
    FEATURED_PROJECT_SUB_TITLE VARCHAR(255) NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample PROJECT_INFO data
INSERT INTO PROJECT_INFO (TYPICAL_PROJECT_TITLE, TYPICAL_PROJECT_SUB_TITLE, FEATURED_PROJECT_TITLE, FEATURED_PROJECT_SUB_TITLE) VALUES 
('Dự án tiêu biểu', 'Khám phá các dự án nội thất đã hoàn thành của chúng tô', 'Dự án nổi bật', 'Những dự án tiêu biểu đã được chúng tôi hoàn thiện, mang đến không gian sống hoàn hảo cho khách hàng');

-- Create PROJECT_INFO_ITEM table to handle the array of items
DROP TABLE IF EXISTS PROJECT_INFO_ITEM;
CREATE TABLE PROJECT_INFO_ITEM (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    PROJECT_INFO_ID BIGINT NOT NULL,
    ITEM_TITLE VARCHAR(200) NOT NULL,
    ITEM_NUMBER VARCHAR(50) NOT NULL,
    REMARK_EN VARCHAR(255),
    REMARK VARCHAR(255),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (PROJECT_INFO_ID) REFERENCES PROJECT_INFO(ID) ON DELETE CASCADE
);

-- Insert sample PROJECT_INFO_ITEM data
INSERT INTO PROJECT_INFO_ITEM (PROJECT_INFO_ID, ITEM_TITLE, ITEM_NUMBER, REMARK_EN, REMARK) VALUES 
(1, 'Dự án hoàn thành', '500+', 'completed_projects', 'Dự án hoàn thành'),
(1, 'Tỉnh thành', '15+', 'provinces', 'Tỉnh thành'),
(1, 'Khách hàng hài lòng', '1000+', 'satisfied_customers', 'Khách hàng hài lòng'),
(1, 'Năm kinh nghiệm', '10+', 'years_experience', 'Năm kinh nghiệm');

-- Create indexes for better performance
CREATE INDEX IDX_USER_AUTH_PROVIDER ON USER(AUTH_PROVIDER_ID);
CREATE INDEX IDX_PRODUCT_STATUS ON PRODUCT(PRODUCT_STATUS_ID);
CREATE INDEX IDX_PRODUCT_CATEGORY ON PRODUCT(CATEGORY_ID);
CREATE INDEX IDX_PRODUCT_BRAND ON PRODUCT(BRAND_ID);
CREATE INDEX IDX_PROJECT_STATUS ON PROJECT(PROJECT_STATUS_ID);
CREATE INDEX IDX_SECTION_TYPE ON SECTION(SECTION_TYPE_ID);
CREATE INDEX IDX_SECTION_ITEM_ORDER ON SECTION_ITEM(`ORDER`);
CREATE INDEX IDX_SPONSOR_ORDER ON SPONSOR(`ORDER`);
CREATE INDEX IDX_QUOTE_TYPE ON QUOTE(QUOTE_TYPE_ID);
CREATE INDEX IDX_QUOTE_ITEM_QUOTE ON QUOTE_ITEM(QUOTE_ID);
CREATE INDEX IDX_PROJECT_INFO_ITEM ON PROJECT_INFO_ITEM(PROJECT_INFO_ID);