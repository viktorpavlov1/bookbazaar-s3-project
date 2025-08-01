import React, { useEffect, useState } from "react";
import BooksList from "./components/BookList";
import BookForm from "./components/BookForm";
import FilterBooks from "./components/FilterBooks";
import axios from "axios";
import { Container, Grid, Box } from "@mui/material";

function App() {
  const [books, setBooks] = useState([]);
  const [filteredBooks, setFilteredBooks] = useState([]);
  const [editingBook, setEditingBook] = useState(null);

  useEffect(() => {
    fetchBooks();
  }, []);

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
      fetchBooks();
      setEditingBook(null);
    } catch (error) {
      console.error("Error saving book:", error);
    }
  };

  const handleDeleteBook = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/books/${id}`);
      fetchBooks();
    } catch (error) {
      console.error("Error deleting book:", error);
    }
  };

  const handleEditBook = (book) => {
    setEditingBook(book);
  };

  const handleFilter = async (filters) => {
    const { query, category } = filters;

    try {
      if (
        (!query || query.trim() === "") &&
        (category === "All" || !category)
      ) {
        setFilteredBooks(books);
        return;
      }

      if (category && category !== "All" && (!query || query.trim() === "")) {
        const response = await axios({
          method: "get",
          url: "http://localhost:8080/api/books/find/category",
          data: category,
          headers: { "Content-Type": "text/plain" }
        });
        setFilteredBooks(response.data.length > 0 ? response.data : []);
        return;
      }

      if (query && query.trim() !== "") {
        const titleRes = await axios({
          method: "get",
          url: "http://localhost:8080/api/books/find/title",
          data: query,
          headers: { "Content-Type": "text/plain" }
        });

        const authorRes = await axios({
          method: "get",
          url: "http://localhost:8080/api/books/find/author",
          data: query,
          headers: { "Content-Type": "text/plain" }
        });

        const merged = [...titleRes.data, ...authorRes.data].filter(
          (book, idx, arr) => idx === arr.findIndex((b) => b.id === book.id)
        );

        const finalFiltered =
          category && category !== "All"
            ? merged.filter(
                (b) => b.category.toLowerCase() === category.toLowerCase()
              )
            : merged;

        setFilteredBooks(finalFiltered.length > 0 ? finalFiltered : []);
        return;
      }
    } catch (error) {
      console.error("Error filtering books:", error);
      setFilteredBooks([]);
    }
  };

  return (
    <Box sx={{ backgroundColor: "#f9f5ec", minHeight: "100vh", py: 3 }}>
      <Container maxWidth="lg">
        {/* ✅ Logo Centered and Bigger */}
        <Box sx={{ textAlign: "center"}}>
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

export default App;
