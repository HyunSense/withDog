import withDogLogoUpdate from "../../assets/images/withdog-logo-950x250.png";
import { Link } from "react-router-dom";

const AuthMainLogo = (logoWidth) => {
  return (
    <Link className="flex justify-center items-center mb-12 pr-7" to={"/"}>
      <img className="w-56" src={withDogLogoUpdate} alt="WithDog 로고" />
    </Link>
  );
};

export default AuthMainLogo;
