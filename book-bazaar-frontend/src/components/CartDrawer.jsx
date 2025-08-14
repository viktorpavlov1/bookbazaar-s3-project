import React from "react";
import {
  Drawer,
  Box,
  Typography,
  IconButton,
  List,
  ListItem,
  ListItemText,
  Divider,
  Button,
  Stack
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import DeleteIcon from "@mui/icons-material/Delete";
import ShoppingCartCheckoutIcon from "@mui/icons-material/ShoppingCartCheckout";
import { useCart } from "../context/CartContext";

export default function CartDrawer({ open, onClose }) {
  const { items, remove, clear } = useCart();
  const total = items.reduce(
    (sum, i) => sum + Number(i.price || 0) * (i.quantity || 1),
    0
  );

  return (
    <Drawer anchor="right" open={open} onClose={onClose}>
      <Box sx={{ width: 360, p: 2 }}>
        <Stack
          direction="row"
          alignItems="center"
          justifyContent="space-between"
        >
          <Typography variant="h6">Your Cart</Typography>
          <IconButton onClick={onClose}>
            <CloseIcon />
          </IconButton>
        </Stack>
        <Divider sx={{ my: 2 }} />
        {items.length === 0 ? (
          <Typography color="text.secondary">Your cart is empty.</Typography>
        ) : (
          <>
            <List>
              {items.map((i) => (
                <ListItem
                  key={i.id}
                  secondaryAction={
                    <IconButton
                      edge="end"
                      onClick={() => remove(i.id)}
                      aria-label="remove"
                    >
                      <DeleteIcon />
                    </IconButton>
                  }
                >
                  <ListItemText
                    primary={`${i.title} × ${i.quantity || 1}`}
                    secondary={`$${Number(i.price || 0).toFixed(2)}`}
                  />
                </ListItem>
              ))}
            </List>
            <Divider sx={{ my: 2 }} />
            <Stack
              direction="row"
              alignItems="center"
              justifyContent="space-between"
            >
              <Typography variant="subtitle1">Total:</Typography>
              <Typography variant="h6">${total.toFixed(2)}</Typography>
            </Stack>
            <Stack direction="row" spacing={1} sx={{ mt: 2 }}>
              <Button onClick={clear}>Clear</Button>
              <Button
                variant="contained"
                startIcon={<ShoppingCartCheckoutIcon />}
                fullWidth
              >
                Checkout (mock)
              </Button>
            </Stack>
          </>
        )}
      </Box>
    </Drawer>
  );
}
