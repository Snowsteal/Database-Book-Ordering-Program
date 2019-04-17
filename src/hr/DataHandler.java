package hr;

import java.sql.*;  // STEP 1. Import required packages
import java.util.*;

public class DataHandler {

    // JBDC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";

    // Database credentials
    static final String USER = "HOMEWORK05";
    static final String PASS = "dbmgmt";

    Connection conn = null;
    PreparedStatement stmt = null;

    public DataHandler() {
        try {
            // Step 2. Register JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Step 3. Open a connection
            System.out.println("Connection to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException se) {
            // Handle errors for JDBC
            System.out.println("Encountered exception:" + se.getMessage());
            conn = null;
            stmt = null;
        } // Handle errors for Class.forName
        finally {
            //finally block used to close resources
        }
    }

    public void Close() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error trying to close.");
        }
    }

    public List<Book> SelectBooks(String isbn, String author, String title, String subject) {
        List<Book> books = new ArrayList<>();
        String sql = "";
        try {
            sql = "Select * from BOOKS";
            
            {
                boolean whereClause = false;
                if(isbn != null) {
                    whereClause = true;
                    sql += " Where isbn = ?";
                }
                
                if(author != null) {
                    if(!whereClause) {
                        whereClause = true;
                        sql += " Where ";
                    } else
                        sql += " And ";
                    sql += "author Like ?";
                }
                
                if(title != null) {
                    if(!whereClause) {
                        whereClause = true;
                        sql += " Where ";
                    } else
                        sql += " And ";
                    sql += "title Like ?";
                }
                
                if(subject != null) {
                    if(!whereClause) {
                        sql += " Where ";
                    } else
                        sql += " And ";
                    sql += "subject = ?";
                }
            }
            
            stmt = conn.prepareStatement(sql);
            
            {
                int counter = 1;
                if(isbn != null) {
                    isbn = String.format("%1$-10s", isbn);
                    stmt.setString(counter, isbn);
                    counter++;
                }
                if(author != null) {
                    author = "%" + author + "%";
                    stmt.setString(counter, author);
                    counter++;
                }
                if(title != null) {
                    title = "%" + title + "%";
                    stmt.setString(counter, title);
                    counter++;
                }
                if(subject != null) {
                    stmt.setString(counter, subject);
                    counter++;
                }
            }
            
            //System.err.println(sql + " " + isbn + " " + author + " " + title + " " + subject);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getFloat("price"),
                        rs.getString("subject")));
            }

        } catch (SQLException e) {
            System.out.println("Encountered exception with sql: " + sql
                    + "\nWith error message: " + e.getMessage());
        }

        return books;
    }

    public Member SelectMember(String userid, String password) {
        Member member = null;
        try {
            String sql = "Select * from MEMBERS Where userid = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userid);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String address = rs.getString("address");
                String city = rs.getString("city");
                String state = rs.getString("state");
                int zip = rs.getInt("zip");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String creditcardtype = rs.getString("creditcardtype");
                String creditcardnumber = rs.getString("creditcardnumber");
                member = new Member(fname, lname, address, city, state, zip, phone, email, userid, password, creditcardtype, creditcardnumber);
            }
        } catch (SQLException e) {
            System.out.println("Encountered exception:" + e.getMessage());
        }

        return member;
    }

    public boolean RegisterMember(Member member) {
        try {
            String sql = "Insert into Members (fname, lname, address, city, state, zip, phone, email, userid, password, creditcardtype, creditcardnumber) "
                    + "Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, member.fname);
            stmt.setString(2, member.lname);
            stmt.setString(3, member.address);
            stmt.setString(4, member.city);
            stmt.setString(5, member.state);
            stmt.setInt(6, member.zip);
            stmt.setString(7, member.phone);
            stmt.setString(8, member.email);
            stmt.setString(9, member.userid);
            stmt.setString(10, member.password);
            stmt.setString(11, member.creditcardtype);
            stmt.setString(12, member.creditcardnumber);
            int result = stmt.executeUpdate();
            if(result != 1) {
                System.out.printf("Result #: %d", result);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
            return false;
        }
        
        return true;
    }
    
    public List<String> SelectSubjects() {
        List<String> subjects = new ArrayList();
        try {
            String sql = "Select Unique subject From BOOKS";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                subjects.add(rs.getString("subject"));
            }
            
        } catch (SQLException e) {
            System.out.println("Exception error: " + e.getLocalizedMessage());
        }
        
        return subjects;
    }
    
    public boolean AddCartItem(CartItem cartItem) {
        try {
            String sql = "Insert into CART(userid, isbn, qty) Values(?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cartItem.userid);
            stmt.setString(2, cartItem.isbn);
            stmt.setInt(3, cartItem.qty);
            
            int result = stmt.executeUpdate();
            if(result != 1) {
                System.out.printf("Result #: %d", result);
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
            return false;
        }
        
        return true;
    }
    
    public boolean DeleteCartItem(String userid, String isbn) {
        try {
            String sql = "Delete From Cart Where userid = ? And isbn = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userid);
            isbn = String.format("%1$-10s", isbn);
            stmt.setString(2, isbn);
            
            int result = stmt.executeUpdate();
            if(result > 0)
                return true;
            
        } catch (SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
        }
        
        return false;
    }
    
    public boolean UpdateCartItem(String userid, String isbn, int qty) {
        try {
            String sql = "Update Cart Set qty = ? Where userid = ? And isbn = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qty);
            stmt.setString(2, userid);
            isbn = String.format("%1$-10s", isbn);
            stmt.setString(3, isbn);
            
            int result = stmt.executeUpdate();
            if(result > 0)
                return true;
            
        } catch (SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
        }
        
        return false;
    }
    
    public List<CartItem> SelectCartItems(String userid) {
        List<CartItem> cartItems = new ArrayList();
        
        try {
            String sql = "Select * From CART_CONTENT Where userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userid);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                cartItems.add(new CartItem(rs.getString("userid"), rs.getString("isbn"), rs.getString("title"), rs.getFloat("price"), rs.getInt("qty")));
            }
            
        } catch (SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
        }
        
        return cartItems;
    }

    public List<Order> SelectOrders(String userid) {
        List<Order> orders = new ArrayList();
        
        try {
            String sql = "Select * From Orders Where userid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userid);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                orders.add(new Order(rs.getString("userid"), rs.getInt("ono"), rs.getDate("received"), 
                        rs.getDate("shipped"), rs.getString("shipAddress"), rs.getString("shipCity"), 
                        rs.getString("shipState"), rs.getInt("shipZip")));
            }
            
        } catch (SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
        }

        return orders;
    }
    
    public Order SelectOrder(int ono) {
        Order order = null;
        
        try {
            String sql = "Select * From Orders Where ono = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ono);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                order = new Order(rs.getString("userid"), rs.getInt("ono"), rs.getDate("received"), 
                        rs.getDate("shipped"), rs.getString("shipAddress"), rs.getString("shipCity"), 
                        rs.getString("shipState"), rs.getInt("shipZip"));
            }
            
        } catch (SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
        }
        
        return order;
    }

    public List<Odetails> SelectOdetails(int ono) {
        List<Odetails> odetails = new ArrayList<>();
        
        try {
            String sql = "Select * From OContent Where ono = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ono);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                odetails.add(new Odetails(rs.getInt("ono"), rs.getString("isbn"), 
                        rs.getString("title"), rs.getInt("qty"), rs.getFloat("price")));
            }
            
        } catch (SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
        }

        return odetails;
    }
    
    public int GenerateNewOrderNo() {
        try {
            String sql = "Select Max(ono) from Orders";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("ono") + 1;
            }
            
        } catch(SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
        }
            
        
        return 0;
    }
    
    public boolean GenerateOrder(Order o, List<Odetails> odetails) {
        try {
          String sql = "Insert into Orders(userid, ono, received, shipped, shipAddress, shipCity, shipState, shipZip) Values(?, ?, ?, null, ?, ?, ?, ?)";
          stmt = conn.prepareStatement(sql);
          stmt.setString(1, o.userid);
          stmt.setInt(2, o.ono);
          stmt.setDate(3, o.received);
          stmt.setString(4, o.shipAddress);
          stmt.setString(5, o.shipCity);
          stmt.setString(6, o.shipState);
          stmt.setInt(7, o.shipZip);
          
          if(stmt.executeUpdate() != 1) {
              return false;
          }
          
          sql = "Insert into Odetails(ono, isbn, qty, price) Values(?, ?, ?, ?)";
          stmt = conn.prepareStatement(sql);
          for(Odetails od : odetails) {
              stmt.setInt(1, od.ono);
              stmt.setString(2, od.isbn);
              stmt.setInt(3, od.qty);
              stmt.setFloat(4, od.price);
              if (stmt.executeUpdate() != 1) {
                  System.out.printf("Error generating order item: Ono: %d\tISBN: %s\tQTY: %d", od.ono, od.isbn, od.qty);
              }
          }
          
        } catch(SQLException e) {
            System.out.println("Some exception occurred: " + e.getLocalizedMessage());
            return false;
        }
        
        return true;
    }
}
