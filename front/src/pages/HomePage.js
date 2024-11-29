import React from "react";
import Header from "../components/common/Header";
import Main from "../components/common/Main";
import Footer from "../components/common/Footer";

function HomePage() {
  // const [isMainRendered, setIsMainRendered] = useState(false);

  // useEffect(() => {
  //   setIsMainRendered(true);
  // }, []);

  return (
    <>
      <Header />
      <Main />
      <Footer />
    </>
  );
}

export default HomePage;
