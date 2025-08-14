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
import { useAuth } from "../context/AuthContext";
import { useCart } from "../context/CartContext";

// Define a palette of colors
const colors = [
  "#6a5acd",
  "#2196f3",
  "#4caf50",
  "#ff9800",
  "#e91e63",
  "#9c27b0"
];

const BooksList = ({ books, onDelete, onEdit }) => {
  const { role } = useAuth();
  const { add } = useCart();
  const isAdmin = role === "admin";

  return (
    <Grid container spacing={3} justifyContent="flex-start">
      {books.length > 0 ? (
        books.map((book, index) => {
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

                <CardActions
                  sx={{
                    justifyContent: "center",
                    pb: 2,
                    gap: 1,
                    flexWrap: "wrap"
                  }}
                >
                  {/* Add to cart: visible to any logged-in user (role !== null). If you want only "user", change condition to role === "user". */}
                  {role && (
                    <Button
                      size="small"
                      variant="outlined"
                      onClick={() => add(book)}
                    >
                      Add to cart
                    </Button>
                  )}
                  {/* Admin-only edit/delete */}
                  <Button
                    size="small"
                    variant="contained"
                    color="success"
                    onClick={() => onEdit(book)}
                    disabled={!isAdmin}
                  >
                    Edit
                  </Button>
                  <Button
                    size="small"
                    variant="contained"
                    color="error"
                    onClick={() => onDelete(book.id)}
                    disabled={!isAdmin}
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
