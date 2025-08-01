import React, { useEffect, useState } from "react";
import { TextField, Paper, Stack, MenuItem, Button } from "@mui/material";

const FilterBooks = ({ onFilter, books }) => {
  const [filters, setFilters] = useState({ query: "", category: "All" });
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    // Extract unique categories dynamically
    if (books.length > 0) {
      const uniqueCategories = [...new Set(books.map((b) => b.category))];
      setCategories(uniqueCategories);
    }
  }, [books]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({ ...prev, [name]: value }));
  };

  const handleSearch = () => {
    onFilter(filters);
  };

  return (
    <Paper elevation={3} sx={{ p: 2 }}>
      <Stack spacing={2} direction="row" alignItems="center">
        {/* ✅ Make this input wider */}
        <TextField
          name="query"
          label="Search by title or author"
          value={filters.query}
          onChange={handleChange}
          sx={{ flexGrow: 2 }} // takes more space
        />

        {/* Category Dropdown */}
        <TextField
          select
          name="category"
          label="Filter by category"
          value={filters.category}
          onChange={handleChange}
          sx={{ flexGrow: 1, minWidth: 180 }}
        >
          <MenuItem value="All">All</MenuItem>
          {categories.map((cat, index) => (
            <MenuItem key={index} value={cat}>
              {cat}
            </MenuItem>
          ))}
        </TextField>

        {/* Search Button */}
        <Button
          variant="contained"
          color="primary"
          onClick={handleSearch}
          sx={{ whiteSpace: "nowrap" }}
        >
          Search
        </Button>
      </Stack>
    </Paper>
  );
};

export default FilterBooks;
