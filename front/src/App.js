import { Navigate, Route, Routes } from "react-router-dom";
import Container from "./components/Container";
import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignupPage";
import HomePage from "./pages/HomePage";
import DetailPage from "./pages/DetailPage";
import { AuthProvider } from "./components/auth/AuthContextProvider";
import PrivateRoute from "./components/auth/PrivateRoute";
import LoginRedirect from "./components/auth/LoginRedirect";
import { GlobalStyle, StyledBackground } from "./styles/GlobalStyle";
import React from "react";
import AdminPage from "./pages/admin/AdminPage";
import AdminEdit from "./components/admin/AdminEdit";
import AdminSave from "./components/admin/AdminSave";
import BookmarkPage from "./pages/user/BookmarkPage";
import NoLoginRedirect from "./components/auth/NoLoginRedirect";
import AdminPlaceEdit from "./components/admin/AdminPlaceEdit";
import AdminSave2 from "./components/admin/AdminSave2";

function App() {
  return (
    <>
      <GlobalStyle />
      <StyledBackground />
      <Container>
        <AuthProvider>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/places" element={<HomePage />} />

            <Route element={<NoLoginRedirect />}>
              <Route path="/myPage/bookmark" element={<BookmarkPage />} />
            </Route>
            <Route element={<LoginRedirect />}>
              <Route path="/login" element={<LoginPage />} />
              <Route path="/sign-up" element={<SignupPage />} />
            </Route>
            {/* PrivateRoute: 부모라우트가 자식라우트르 보호하는 역할 */}
            <Route element={<PrivateRoute roles={["ROLE_ADMIN"]} />}>
              <Route path="/admin" element={<AdminPage />}>
                <Route index element={<Navigate to="edit" replace />} />
                {/* <Route path="register" element={<AdminSave />} /> */}
                <Route path="edit" element={<AdminEdit />} />
                {/* <Route path="edit/:id" element={<AdminPlaceEdit />} /> */}
                <Route path="register" element={<AdminSave2 />} />
                <Route path="edit/:id" element={<AdminPlaceEdit />} />
              </Route>
              {/* <Route path="/admin/edit/:id" element={<AdminPlaceEditPage />} /> */}
            </Route>
            <Route
              path="/places/:category/:name/:id"
              element={<DetailPage />}
            />
          </Routes>
        </AuthProvider>
      </Container>
    </>
  );
}

export default App;
