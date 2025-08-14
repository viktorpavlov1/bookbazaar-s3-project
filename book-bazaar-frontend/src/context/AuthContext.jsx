import React, {
  createContext,
  useContext,
  useEffect,
  useMemo,
  useState
} from "react";
import axios from "axios";

const AuthContext = createContext(null);
const USERS_BASE =
  process.env.REACT_APP_USERS_API ?? "http://localhost:8080/api/users";

// Decode a JWT safely (handles URL-safe base64 and Unicode)
const parseJwt = (token) => {
  try {
    const base64 = token.split(".")[1].replace(/-/g, "+").replace(/_/g, "/");
    const json = decodeURIComponent(
      atob(base64)
        .split("")
        .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
        .join("")
    );
    return JSON.parse(json);
  } catch {
    return null;
  }
};

// Map various role strings to "admin" | "user"
const normalizeRole = (role) => {
  if (!role) return "user";
  const r = String(role).toLowerCase();
  if (r.includes("admin")) return "admin";
  if (r.includes("user")) return "user";
  return "user";
};

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    try {
      return JSON.parse(localStorage.getItem("bb_user")) || null;
    } catch {
      return null;
    }
  });
  const [role, setRole] = useState(
    () => localStorage.getItem("bb_role") || null
  );
  const [token, setToken] = useState(
    () => localStorage.getItem("bb_token") || null
  );
  const [loading, setLoading] = useState(false);

  // Persist to localStorage
  useEffect(() => {
    if (user) localStorage.setItem("bb_user", JSON.stringify(user));
    else localStorage.removeItem("bb_user");

    if (role) localStorage.setItem("bb_role", role);
    else localStorage.removeItem("bb_role");

    if (token) localStorage.setItem("bb_token", token);
    else localStorage.removeItem("bb_token");
  }, [user, role, token]);

  // Set/unset Authorization header globally
  useEffect(() => {
    if (token) {
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    } else {
      delete axios.defaults.headers.common["Authorization"];
    }
  }, [token]);

  // ---- Public API ----
  const login = async ({ username, password }) => {
    setLoading(true);
    try {
      // Backend returns a JWT string
      const res = await axios.post(
        `${USERS_BASE}/login`,
        { username, password },
        { headers: { "Content-Type": "application/json" } }
      );

      const tok = res?.data;
      if (typeof tok !== "string" || tok.split(".").length !== 3) {
        return { ok: false, error: "Login did not return a valid token." };
      }

      const payload = parseJwt(tok);
      if (!payload?.sub) return { ok: false, error: "Invalid token payload." };

      const roleNorm = normalizeRole(payload.role);
      setUser({ username: payload.sub, id: payload.userId });
      setRole(roleNorm);
      setToken(tok);

      return { ok: true };
    } catch (e) {
      console.error("Login failed:", e);
      return { ok: false, error: "Invalid credentials or server error." };
    } finally {
      setLoading(false);
    }
  };

  const register = async (userDto) => {
    setLoading(true);
    try {
      await axios.post(`${USERS_BASE}/newUser`, userDto, {
        headers: { "Content-Type": "application/json" }
      });
      // Auto-login using the same credentials
      const { username, password } = userDto;
      if (username && password) {
        return await login({ username, password });
      }
      return { ok: true };
    } catch (e) {
      console.error("Register failed:", e);
      return { ok: false, error: "Registration failed." };
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setUser(null);
    setRole(null);
    setToken(null);
  };

  const value = useMemo(
    () => ({ user, role, token, loading, login, register, logout }),
    [user, role, token, loading]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
