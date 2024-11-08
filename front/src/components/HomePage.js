import React, { useEffect, useState } from "react";
import Header from "./Header";
import Main from "./Main";
import Footer from "./Footer";

function HomePage() {
  const [isMainRendered, setIsMainRendered] = useState(false);

  useEffect(() => {
    setIsMainRendered(true);
  }, []);

  return (
    <>
      <Header />
      <Main />
      <Footer />
    </>
  );
}

export default HomePage;
