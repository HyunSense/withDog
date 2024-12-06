import React, { useContext } from "react";
import Header from "../components/common/Header";
import Main from "../components/common/Main";
import Footer from "../components/common/Footer";
import { AuthContext } from "../components/auth/AuthContextProvider";
import { useLocation } from "react-router-dom";

function HomePage() {
  // const {isLogin} = useContext(AuthContext);
  // const location = useLocation();
  
  // const isBookmarkPage = location.pathname === "/bookmark";

  return (
    <>
      <Header />
      {/* {isBookmarkPage && isLogin ? <Main showBookmark /> : <Main />} */}
      {/* {!isBookmarkPage && <Footer />} */}
      <Main />
      <Footer />
    </>
  );
}

export default HomePage;
