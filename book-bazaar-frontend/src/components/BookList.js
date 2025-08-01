import React from "react";
import {
  Grid,
  Card,
  CardContent,
  Typography,
  Button,
  CardActions
} from "@mui/material";
import MenuBookIcon from "@mui/icons-material/MenuBook";

// Define a palette of colors
const colors = [
  "#6a5acd", // purple
  "#2196f3", // blue
  "#4caf50", // green
  "#ff9800", // orange
  "#e91e63", // pink
  "#9c27b0" // violet
];

const BooksList = ({ books, onDelete, onEdit }) => {
  return (
    <Grid container spacing={3} justifyContent="flex-start">
      {books.length > 0 ? (
        books.map((book, index) => {
          // Pick a color based on index (rotates through palette)
          const iconColor = colors[index % colors.length];

          return (
            <Grid
              item
              key={book.id}
              xs={12}
              sm={6}
              md={4}
              lg={3}
              xl={2.4}
              sx={{ display: "flex", justifyContent: "center" }}
            >
              <Card
                elevation={3}
                sx={{
                  width: "250px",
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "space-between"
                }}
              >
                <CardContent>
                  <MenuBookIcon
                    sx={{
                      fontSize: 40,
                      color: iconColor,
                      display: "block",
                      margin: "0 auto 8px auto"
                    }}
                  />
                  <Typography variant="h6" noWrap>
                    {book.title}
                  </Typography>
                  <Typography>
                    <strong>Author:</strong> {book.author}
                  </Typography>
                  <Typography>
                    <strong>Category:</strong> {book.category}
                  </Typography>
                  <Typography>
                    <strong>Price:</strong> ${book.price}
                  </Typography>
                  <Typography>
                    <strong>Quantity:</strong> {book.quantity}
                  </Typography>
                  <Typography>
                    <strong>Year:</strong> {book.year}
                  </Typography>
                  <Typography
                    variant="body2"
                    sx={{
                      overflow: "hidden",
                      textOverflow: "ellipsis",
                      display: "-webkit-box",
                      WebkitLineClamp: 3,
                      WebkitBoxOrient: "vertical"
                    }}
                  >
                    <strong>Description:</strong> {book.description}
                  </Typography>
                </CardContent>
                <CardActions sx={{ justifyContent: "center", pb: 2 }}>
                  <Button
                    size="small"
                    variant="contained"
                    color="success"
                    onClick={() => onEdit(book)}
                  >
                    Edit
                  </Button>
                  <Button
                    size="small"
                    variant="contained"
                    color="error"
                    onClick={() => onDelete(book.id)}
                  >
                    Delete
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          );
        })
      ) : (
        <Typography variant="h6" sx={{ m: 2 }}>
          No books available
        </Typography>
      )}
    </Grid>
  );
};

export default BooksList;
