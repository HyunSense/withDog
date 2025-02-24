import styled from "styled-components";
import withDogLogoUpdate from "../../assets/images/withdog-logo-950x250.png";
import { Link } from "react-router-dom";

const StyledAuthLogoBox = styled(Link)`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 50px;
  padding-right: 30px;
`;

const StyledAuthLogo = styled.img`
  width: 220px;
  /* height: auto; */
  width: ${({ $width }) => $width};
`;

const AuthMainLogo = (logoWidth) => {
  return (
    <StyledAuthLogoBox to={"/"}>
      <StyledAuthLogo src={withDogLogoUpdate} width={logoWidth} />
    </StyledAuthLogoBox>
  );
};

export default AuthMainLogo;
