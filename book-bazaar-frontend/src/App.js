// src/App.js
import React, { useEffect, useState } from "react";
import BooksList from "./components/BookList";
import BookForm from "./components/BookForm";
import FilterBooks from "./components/FilterBooks";
import axios from "axios";
import {
  Container,
  Grid,
  Box,
  AppBar,
  Toolbar,
  Typography,
  Button,
  IconButton,
  Avatar,
  Tooltip
} from "@mui/material";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import LoginDialog from "./components/LoginDialog";
import CartDrawer from "./components/CartDrawer";
import { AuthProvider, useAuth } from "./context/AuthContext";
import { CartProvider } from "./context/CartContext";
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";
import ProfilePage from "./pages/ProfilePage";

function HomeContent({
  books,
  filteredBooks,
  handleFilter,
  handleAddBook,
  handleDeleteBook,
  handleEditBook,
  editingBook
}) {
  return (
    <Box sx={{ backgroundColor: "#f9f5ec", minHeight: "100vh", py: 3 }}>
      <Container maxWidth="lg">
        <Box sx={{ textAlign: "center" }}>
          <img
            src="/bookbazaar-logo.png"
            alt="BookBazaar Logo"
            style={{ height: "200px", maxWidth: "100%", objectFit: "contain" }}
          />
        </Box>

        <Grid container spacing={2} justifyContent="center" sx={{ mb: 3 }}>
          <Grid item xs={12} md={4}>
            <FilterBooks onFilter={handleFilter} books={books} />
          </Grid>
          <Grid item xs={12} md={8}>
            {/* BookForm hides itself entirely for non-admins */}
            <BookForm onSubmit={handleAddBook} editingBook={editingBook} />
          </Grid>
        </Grid>

        <BooksList
          books={filteredBooks}
          onDelete={handleDeleteBook}
          onEdit={handleEditBook}
        />
      </Container>
    </Box>
  );
}

function AppShell() {
  const [books, setBooks] = useState([]);
  const [filteredBooks, setFilteredBooks] = useState([]);
  const [editingBook, setEditingBook] = useState(null);
  const [loginOpen, setLoginOpen] = useState(false);
  const [cartOpen, setCartOpen] = useState(false);

  const { user, role, logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    fetchBooks();
  }, []);

  // Optional: log profile once after sign-in
  useEffect(() => {
    if (!user?.username) return;
    const url = `http://localhost:8080/api/users/specificUser/${encodeURIComponent(
      user.username
    )}`;
    axios
      .get(url)
      .then((res) => console.log("User profile from backend:", res.data))
      .catch((err) => console.error("Error fetching user profile:", err));
  }, [user?.username]);

  const fetchBooks = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/books/all");
      setBooks(response.data);
      setFilteredBooks(response.data);
    } catch (error) {
      console.error("Error fetching books:", error);
    }
  };

  const handleAddBook = async (book) => {
    try {
      if (editingBook) {
        await axios.put("http://localhost:8080/api/books/update", book);
      } else {
        await axios.post("http://localhost:8080/api/books/new", book);
      }
      await fetchBooks();
      setEditingBook(null);
    } catch (error) {
      console.error("Error saving book:", error);
    }
  };

  const handleDeleteBook = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/books/${id}`);
      await fetchBooks();
    } catch (error) {
      console.error("Error deleting book:", error);
    }
  };

  const handleEditBook = (book) => setEditingBook(book);

  // ✅ Client-side filtering (replaces previous server calls with GET bodies)
  const handleFilter = (filters) => {
    const { query, category } = filters;

    let result = [...books];

    const q = (query || "").trim().toLowerCase();
    if (q) {
      result = result.filter((b) => {
        const title = (b.title || "").toLowerCase();
        const author = (b.author || "").toLowerCase();
        return title.includes(q) || author.includes(q);
      });
    }

    if (category && category !== "All") {
      const cat = category.toLowerCase();
      result = result.filter((b) => (b.category || "").toLowerCase() === cat);
    }

    setFilteredBooks(result);
  };

  const initial = (user?.username ?? "U").charAt(0).toUpperCase();

  return (
    <>
      <AppBar
        position="sticky"
        elevation={0}
        sx={{
          backgroundColor: "#fff",
          color: "inherit",
          borderBottom: "1px solid #eee"
        }}
      >
        <Toolbar>
          <Typography
            variant="h6"
            sx={{ flexGrow: 1, cursor: "pointer" }}
            onClick={() => navigate("/")}
          >
            BookBazaar
          </Typography>

          {/* Cart button for signed-in users */}
          {role && (
            <Tooltip title="Open cart">
              <IconButton onClick={() => setCartOpen(true)} size="large">
                <ShoppingCartIcon />
              </IconButton>
            </Tooltip>
          )}

          {user ? (
            <>
              {/* Profile button: avatar + username together */}
              <Button
                color="inherit"
                onClick={() => navigate("/profile")}
                startIcon={
                  <Avatar sx={{ width: 24, height: 24 }}>{initial}</Avatar>
                }
                sx={{ textTransform: "none", mr: 1 }}
                aria-label="Open profile"
              >
                {user.username}
              </Button>
              <Button onClick={logout} color="inherit">
                Logout
              </Button>
            </>
          ) : (
            <Button variant="outlined" onClick={() => setLoginOpen(true)}>
              Sign In
            </Button>
          )}
        </Toolbar>
      </AppBar>

      <Routes>
        <Route
          path="/"
          element={
            <HomeContent
              books={books}
              filteredBooks={filteredBooks}
              handleFilter={handleFilter}
              handleAddBook={handleAddBook}
              handleDeleteBook={handleDeleteBook}
              handleEditBook={handleEditBook}
              editingBook={editingBook}
            />
          }
        />
        <Route path="/profile" element={<ProfilePage />} />
      </Routes>

      <LoginDialog open={loginOpen} onClose={() => setLoginOpen(false)} />
      <CartDrawer open={cartOpen} onClose={() => setCartOpen(false)} />
    </>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <CartProvider>
          <AppShell />
        </CartProvider>
      </AuthProvider>
    </BrowserRouter>
  );
}
