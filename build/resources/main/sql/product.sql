create type product_condition AS ENUM ('NEW', 'OLD');
create type special_features AS ENUM ('Curved', 'Flicker Free', 'Blue Light', 'Tilt Adjustment', 'Anti Glare Screen');
create type brands_available AS ENUM ('Dell', 'Samsung', 'HP', 'BenQ');
create type connectivity_tech AS ENUM ('HDMI', 'USBC', 'DGVA', 'VGA', 'D-Sub');
create type colors AS ENUM ('Black', 'White');
create type display_tech AS ENUM ('LCD', 'LED', 'IPS');
create type refresh_rate AS ENUM ('100Hz', '170Hz', '165Hz', '175Hz');

create table "product" (
                         id serial primary key,
                         price real,
                         name varchar(100) not null,
                         affiliateURL varchar(500) not null,
                         productCondition product_condition,
                         screenSize varchar(25),
                         maxDisplayResolution varchar(25),
                         brand varchar(10),
                         brandSeries varchar(10),
                         hdmiPortsQty smallserial,
                         refreshRate refresh_rate,
                         connectivityTech connectivity_tech,
                         aspectRatio varchar(5),
                         displayType display_tech,
                         dimension varchar(25),
                         warranty varchar(25),
                         specialFeatures special_features,
                         color colors,
                         amazonChoice boolean,
                         purveyor varchar(10)
);
