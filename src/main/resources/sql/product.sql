create type product_condition AS ENUM ('NEW', 'OLD');
create type colors AS ENUM ('Black', 'White');
create type display_tech AS ENUM ('LCD', 'LED', 'IPS', 'HD');
create type refresh_rate AS ENUM ('60Hz', '100Hz', '170Hz', '165Hz', '175Hz');

create table "product" (
                         id serial primary key,
                         price real,
                         name varchar(100) not null,
                         affiliateURL varchar(5000) not null,
                         productCondition product_condition,
                         screenSize varchar(25),
                         maxDisplayResolution varchar(25),
                         brand varchar(10),
                         brandSeries varchar(10),
                         hdmiPortsQty smallserial,
                         refreshRate refresh_rate,
                         connectivityTech text[][],
                         aspectRatio varchar(5),
                         displayType display_tech,
                         dimension varchar(100),
                         warranty varchar(25),
                         specialFeatures text[][],
                         color colors,
                         amazonChoice boolean,
                         purveyor varchar(10)
);
