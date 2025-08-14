import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Container,
  Card,
  CardContent,
  Typography,
  Grid,
  Button,
  Stack,
  Divider
} from "@mui/material";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function ProfilePage() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user?.username) return;
    const url = `http://localhost:8080/api/users/specificUser/${encodeURIComponent(
      user.username
    )}`;
    axios
      .get(url)
      .then((res) => setProfile(res.data))
      .catch((err) => console.error("Error fetching user profile:", err))
      .finally(() => setLoading(false));
  }, [user?.username]);

  const Row = ({ label, value }) => (
    <Grid item xs={12} sm={6}>
      <Typography variant="subtitle2" color="text.secondary">
        {label}
      </Typography>
      <Typography variant="body1" sx={{ wordBreak: "break-word" }}>
        {value ?? "—"}
      </Typography>
    </Grid>
  );

  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Stack
        direction="row"
        alignItems="center"
        justifyContent="space-between"
        sx={{ mb: 2 }}
      >
        <Typography variant="h5">Profile</Typography>
        <Button variant="outlined" onClick={() => navigate("/")}>
          Back
        </Button>
      </Stack>

      <Card elevation={3}>
        <CardContent>
          {loading ? (
            <Typography>Loading…</Typography>
          ) : !profile ? (
            <Typography color="error">No profile data.</Typography>
          ) : (
            <>
              <Typography variant="h6" sx={{ mb: 1 }}>
                {profile.name || profile.username}
              </Typography>
              <Divider sx={{ mb: 2 }} />
              <Grid container spacing={2}>
                <Row label="ID" value={profile.id} />
                <Row label="Username" value={profile.username} />
                {/* Password intentionally omitted */}
                <Row label="Role" value={profile.role} />
                <Row label="Email" value={profile.email} />
                <Row label="Phone" value={profile.phone} />
                <Row label="Address" value={profile.address} />
                <Row label="Birthdate" value={profile.birthdate} />
                <Row label="Abbreviation" value={profile.abbreviation} />
                <Row label="Favourite Book" value={profile.favouriteBook} />
              </Grid>
            </>
          )}
        </CardContent>
      </Card>
    </Container>
  );
}
