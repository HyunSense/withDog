import styled from "styled-components";

export const StyledFooter = styled.footer`
  position: relative;
  padding: 10px 15px;
  background-color: #f8fafb;
`;

export const StyledText = styled.p`
  font-size: 1.2rem;
  color: #4e5354;
  font-weight: ${({ $fontWeight }) => $fontWeight};
  text-decoration: ${({$textDecoration}) => $textDecoration};
`;

export const StyledTextBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px 0;
  border-top: 1px solid #dfdfdf;
  /* border-bottom: 1px solid #dfdfdf; */
  padding: 5px 0;
`;

export const StyledCopyRightBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2px 0;
`;

export const StyledIconBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 5px;
`;

export const StyledLogoBox = styled.div`
  width: 70px;
`;

export const StyledLogo = styled.img`
  width: 100%;
  height: 100%;
`;

export const StyledSnsBox = styled.div`
  display: flex;
  align-items: center;
  gap: 0 15px;
`;

export const StyledSnsLogoBox = styled.div`
  width: 20px;
  height: auto;
`;

export const StyledSnsLogo = styled.img`
  width: 100%;
  height: 100%;
`;