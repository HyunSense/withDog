import styled from "styled-components";

const StyledText = styled.p`
  font-size: ${({fontSize}) => fontSize || "1.4rem"};
  font-weight: ${({fontWeight}) => fontWeight || "normal"};
  color: ${({color}) => color || "#000000"};
  margin-top: ${({$marginTop}) => $marginTop || "0"};
  width: ${({width}) => width};
  padding: ${({$padding}) => $padding};
`;

export default StyledText;
