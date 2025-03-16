import { Navigate, Route, Routes } from "react-router-dom";
import Container from "./components/Container";
import LoginPage from "./components/pages/LoginPage";
import SignupPage from "./components/pages/SignupPage";
import HomePage from "./components/pages/HomePage";
import DetailPage from "./components/pages/DetailPage";
import BookmarkPage from "./components/pages/user/BookmarkPage";
import AdminPage from "./components/pages/admin/AdminPage";
import { AuthProvider } from "./components/auth/AuthContextProvider";
import PrivateRoute from "./components/auth/PrivateRoute";
import LoginRedirect from "./components/auth/LoginRedirect";
import React from "react";
import AdminEdit from "./components/admin/AdminEdit";
import NoLoginRedirect from "./components/auth/NoLoginRedirect";
import AdminPlaceEdit from "./components/admin/AdminPlaceEdit";
import AdminPlaceSave from "./components/admin/AdminPlaceSave";
import Oauth2Redirect from "./components/auth/Oauth2Redirect";
import useViewportHeight from "./hooks/useViewportHeight";
import SearchResult from "./components/pages/SearchResultPage";

function App() {
  useViewportHeight();
  return (
    <>
      <div className="app-background" />
      <Container>
        <AuthProvider>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/places" element={<HomePage />} />
            <Route path="/search/result" element={<SearchResult />} />
            <Route path="/oauth2/redirect" element={<Oauth2Redirect />} />

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
                <Route path="edit" element={<AdminEdit />} />
                <Route path="register" element={<AdminPlaceSave />} />
                <Route path="edit/:id" element={<AdminPlaceEdit />} />
              </Route>
            </Route>
            <Route path="/places/:id" element={<DetailPage />} />
            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </AuthProvider>
      </Container>
    </>
  );
}

export default App;
