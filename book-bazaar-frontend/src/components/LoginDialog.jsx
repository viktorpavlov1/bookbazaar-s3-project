import React, { useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Stack,
  Alert,
  Tabs,
  Tab
} from "@mui/material";
import { useAuth } from "../context/AuthContext";

export default function LoginDialog({ open, onClose }) {
  const { login, register, loading } = useAuth();
  const [mode, setMode] = useState("login"); // "login" | "register"
  const [error, setError] = useState(null);

  const [form, setForm] = useState({
    // shared
    username: "",
    password: "",
    // register-only fields
    name: "",
    role: "user",
    address: "",
    email: "",
    phone: "",
    birthdate: "", // HTML date input returns "yyyy-MM-dd"
    abbreviation: "",
    favouriteBook: ""
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async () => {
    setError(null);
    if (mode === "login") {
      const res = await login({
        username: form.username,
        password: form.password
      });
      if (res.ok) onClose();
      else setError(res.error);
    } else {
      // Sends all fields exactly as named in your JSON example
      const res = await register(form);
      if (res.ok) onClose();
      else setError(res.error);
    }
  };

  const switchMode = (_, v) => {
    setMode(v);
    setError(null);
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>{mode === "login" ? "Sign In" : "Register"}</DialogTitle>
      <DialogContent>
        <Tabs value={mode} onChange={switchMode} sx={{ mb: 2 }}>
          <Tab label="Sign In" value="login" />
          <Tab label="Register" value="register" />
        </Tabs>

        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        {mode === "login" ? (
          <Stack spacing={2}>
            <TextField
              name="username"
              label="Username"
              value={form.username}
              onChange={handleChange}
              fullWidth
              autoFocus
            />
            <TextField
              name="password"
              label="Password"
              type="password"
              value={form.password}
              onChange={handleChange}
              fullWidth
            />
          </Stack>
        ) : (
          <Stack spacing={2}>
            <TextField
              name="name"
              label="Name"
              value={form.name}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="username"
              label="Username"
              value={form.username}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="password"
              label="Password"
              type="password"
              value={form.password}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="role"
              label="Role"
              value={form.role}
              onChange={handleChange}
              fullWidth
              placeholder="user or admin"
            />
            <TextField
              name="address"
              label="Address"
              value={form.address}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="email"
              label="Email"
              type="email"
              value={form.email}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="phone"
              label="Phone"
              value={form.phone}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="birthdate"
              label="Birthdate"
              type="date"
              value={form.birthdate}
              onChange={handleChange}
              fullWidth
              InputLabelProps={{ shrink: true }}
              helperText="Format: yyyy-MM-dd"
            />
            <TextField
              name="abbreviation"
              label="Abbreviation"
              value={form.abbreviation}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="favouriteBook"
              label="Favourite Book"
              value={form.favouriteBook}
              onChange={handleChange}
              fullWidth
            />
          </Stack>
        )}
      </DialogContent>

      <DialogActions>
        <Button onClick={onClose} disabled={loading}>
          Cancel
        </Button>
        <Button variant="contained" onClick={handleSubmit} disabled={loading}>
          {mode === "login" ? "Sign In" : "Create Account"}
        </Button>
      </DialogActions>
    </Dialog>
  );
}
