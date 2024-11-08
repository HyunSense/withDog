import styled, { createGlobalStyle } from "styled-components";
import { Route, Routes } from "react-router-dom";
import Container from "./components/Container";
import LoginPage from "./components/LoginPage";
import SignupPage from "./components/SignupPage";
import HomePage from "./components/HomePage";
import RegisterPage from "./components/RegisterPage";
import DetailPage from "./components/DetailPage";
import PlaceList from "./components/PlaceList";

const GlobalStyle = createGlobalStyle`

  html {

    font-size: 62.5%;// (1rem = 10px)
  }
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Spoqa Han Sans Neo', 'sans-serif'
  }

  a {
    outline: none;
    text-decoration: none;

    &:hover {
      text-decoration: none;
    }
  }

  
  /* body {
    font-family: 'Spoqa Han Sans Neo', 'sans-serif';
  } */
`;

const StyledBackground = styled.div`
    position: fixed;
    z-index: -1;
    width: 100vw;
    height: 100%;
    background-size: cover;
    background-repeat: no-repeat;
    background-color: rgb(243, 246, 246);
`;

function App() {
  return (
    <>
      <GlobalStyle />
      <StyledBackground />
      <Container>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/places" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/sign-up" element={<SignupPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/detail" element={<DetailPage />} />
          {/* <Route path="/places/:category/:id" element={<DetailPage />} /> */}
          <Route path="/places/:category/:name/:id" element={<DetailPage />} />
        </Routes>
      </Container>
    </>
  );
}

export default App;
