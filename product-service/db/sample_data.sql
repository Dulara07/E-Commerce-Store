-- Sample data for the "product" table (com.freshcart.product_service.entity.Product)
-- Safe to run against a fresh Azure Database for PostgreSQL instance.
-- If the app has never started yet (so Hibernate hasn't created the table via
-- spring.jpa.hibernate.ddl-auto=update), the CREATE TABLE below builds a
-- matching schema. If the table already exists, it's left as-is.

CREATE TABLE IF NOT EXISTS product (
    product_id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name            VARCHAR(150) NOT NULL,
    description     VARCHAR(500),
    category        VARCHAR(100),
    unit_price      NUMERIC(12,2) NOT NULL,
    stock_quantity  INTEGER NOT NULL DEFAULT 0
);

INSERT INTO product (name, description, category, unit_price, stock_quantity) VALUES
('Bananas', 'Fresh Cavendish bananas, sold per kg', 'Produce', 0.59, 500),
('Roma Tomatoes', 'Vine-ripened Roma tomatoes, per kg', 'Produce', 1.29, 350),
('Baby Spinach', 'Washed and ready-to-eat baby spinach, 200g bag', 'Produce', 2.49, 220),
('Avocado', 'Hass avocado, sold individually', 'Produce', 0.99, 300),
('Whole Milk', 'Pasteurized whole milk, 1L carton', 'Dairy', 1.79, 400),
('Cheddar Cheese', 'Aged cheddar cheese block, 250g', 'Dairy', 3.99, 150),
('Greek Yogurt', 'Plain Greek yogurt, 500g tub', 'Dairy', 2.99, 180),
('Free-Range Eggs', 'Free-range eggs, box of 12', 'Dairy', 3.49, 260),
('Sourdough Bread', 'Freshly baked sourdough loaf, 500g', 'Bakery', 3.29, 90),
('Croissants', 'Butter croissants, pack of 4', 'Bakery', 4.49, 75),
('Whole Wheat Bagels', 'Whole wheat bagels, pack of 6', 'Bakery', 3.79, 110),
('Orange Juice', '100% pure orange juice, 1L bottle', 'Beverages', 2.99, 240),
('Sparkling Water', 'Naturally sparkling mineral water, 1L bottle', 'Beverages', 1.19, 500),
('Ground Coffee', 'Medium roast ground coffee, 250g bag', 'Beverages', 6.99, 130),
('Potato Chips', 'Sea salt flavored potato chips, 150g bag', 'Snacks', 1.99, 320),
('Mixed Nuts', 'Roasted and salted mixed nuts, 300g bag', 'Snacks', 5.49, 140),
('Dark Chocolate Bar', '70% cocoa dark chocolate bar, 100g', 'Snacks', 2.49, 260),
('Chicken Breast', 'Boneless skinless chicken breast, per kg', 'Meat', 7.99, 160),
('Ground Beef', '85% lean ground beef, per kg', 'Meat', 8.49, 140),
('Smoked Salmon', 'Cold-smoked Atlantic salmon, 150g pack', 'Meat', 6.99, 80),
('Frozen Mixed Vegetables', 'Peas, carrots, and corn mix, 500g bag', 'Frozen', 2.29, 210),
('Frozen Margherita Pizza', 'Thin crust margherita pizza, 400g', 'Frozen', 4.99, 95),
('Vanilla Ice Cream', 'Classic vanilla ice cream, 1L tub', 'Frozen', 3.99, 120),
('Dish Soap', 'Lemon-scented dish soap, 500ml bottle', 'Household', 2.19, 300),
('Paper Towels', 'Absorbent paper towels, pack of 6 rolls', 'Household', 5.99, 180);
