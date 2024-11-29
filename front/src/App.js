import { Route, Routes } from "react-router-dom";
import Container from "./components/Container";
import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignupPage";
import HomePage from "./pages/HomePage";
import RegisterPage from "./pages/RegisterPage";
import DetailPage from "./pages/DetailPage";
import { AuthProvider } from "./components/auth/AuthContextProvider";
import PrivateRoute from "./components/auth/PrivateRoute";
import LoginRedirect from "./components/auth/LoginRedirect";
import { GlobalStyle, StyledBackground } from "./styles/GlobalStyle";
import React from "react";

// const LoginPage = React.lazy(() => import("./pages/LoginPage"));
// const SignupPage = React.lazy(() => import("./pages/SignupPage"));
// const HomePage = React.lazy(() => import("./pages/HomePage"));
// const RegisterPage = React.lazy(() => import("./pages/RegisterPage"));
// const DetailPage = React.lazy(() => import("./pages/DetailPage"));

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
            <Route
              path="/login"
              element={
                <LoginRedirect>
                  <LoginPage />
                </LoginRedirect>
              }
            />

            <Route
              path="/sign-up"
              element={
                <LoginRedirect>
                  <SignupPage />
                </LoginRedirect>
              }
            />

            {/* PrivateRoute: 부모라우트가 자식라우트르 보호하는 역할 */}
            <Route element={<PrivateRoute roles={["ROLE_ADMIN"]} />}>
              <Route path="/register" element={<RegisterPage />} />
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
