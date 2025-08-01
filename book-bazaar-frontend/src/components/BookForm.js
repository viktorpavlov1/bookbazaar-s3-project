import React, { useState, useEffect } from "react";
import { TextField, Button, Paper, Stack, Grid } from "@mui/material";

const BookForm = ({ onSubmit, editingBook }) => {
  const [book, setBook] = useState({
    title: "",
    author: "",
    category: "",
    price: "",
    year: "",
    description: "",
    quantity: ""
  });

  useEffect(() => {
    if (editingBook) {
      setBook(editingBook);
    }
  }, [editingBook]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBook((prevBook) => ({ ...prevBook, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(book);
    setBook({
      title: "",
      author: "",
      category: "",
      price: "",
      year: "",
      description: "",
      quantity: ""
    });
  };

  return (
    <Paper elevation={3} sx={{ p: 2 }}>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={12} sm={2}>
            <TextField
              label="Title"
              name="title"
              value={book.title}
              onChange={handleChange}
              required
              fullWidth
            />
          </Grid>
          <Grid item xs={12} sm={2}>
            <TextField
              label="Author"
              name="author"
              value={book.author}
              onChange={handleChange}
              required
              fullWidth
            />
          </Grid>
          <Grid item xs={12} sm={2}>
            <TextField
              label="Category"
              name="category"
              value={book.category}
              onChange={handleChange}
              required
              fullWidth
            />
          </Grid>
          <Grid item xs={12} sm={1.5}>
            <TextField
              label="Price"
              name="price"
              type="number"
              value={book.price}
              onChange={handleChange}
              required
              fullWidth
            />
          </Grid>
          <Grid item xs={12} sm={1.5}>
            <TextField
              label="Year"
              name="year"
              type="number"
              value={book.year}
              onChange={handleChange}
              required
              fullWidth
            />
          </Grid>
          <Grid item xs={12} sm={2}>
            <TextField
              label="Description"
              name="description"
              value={book.description}
              onChange={handleChange}
              fullWidth
            />
          </Grid>
          <Grid item xs={12} sm={1.5}>
            <TextField
              label="Quantity"
              name="quantity"
              type="number"
              value={book.quantity}
              onChange={handleChange}
              required
              fullWidth
            />
          </Grid>
          <Grid item xs={12} sm={1}>
            <Button type="submit" variant="contained" color="primary" fullWidth>
              {editingBook ? "Update" : "Add"}
            </Button>
          </Grid>
        </Grid>
      </form>
    </Paper>
  );
};

export default BookForm;
