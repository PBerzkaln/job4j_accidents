INSERT INTO accidents(name, text, address, type_id)
SELECT 'Авария при перестроение', 'Ауди не пропустило Опель при смене полосы', 'Волоколамское ш. 25', tp.id
FROM accident_types AS tp
WHERE tp.type_name = 'Две машины' LIMIT 1;

INSERT INTO accidents(name, text, address, type_id)
SELECT 'Наезд на велосипедиста', 'Кадиллак сбил велосипедиста на пешеходном переходе ', 'Коломенский проезд 18', tp.id
FROM accident_types AS tp
WHERE tp.type_name = 'Машина и велосипед' LIMIT 1;