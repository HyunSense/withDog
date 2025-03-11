import styled from "styled-components";

export const StyledDetailMain = styled.main`
  
`;

export const StyledDetailInfoBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 20px;
  gap: 12px 0;
  padding: 0 15px;
`;

export const StyledDetailInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 0 10px;
  margin: ${({ $margin }) => $margin};
  white-space: nowrap;
`;

export const StyledDetailReservationLink = styled.a`
  display: block;
  margin-top: 20px;
  outline: none;
  text-decoration: none;
  padding: 0 15px;
  cursor: pointer;
`;

export const StyledDetailInfoBlogBox = styled.div`
  margin-top: 10px;
  padding: 20px 15px;
`;

export const StyledDetailInfoBlogList = styled.div`
  margin-top: 15px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px 10px;
`;

export const StyledDetailInfoBlogLink = styled.a`
  outline: none;
  text-decoration: none;
  color: black;
  cursor: pointer;
`;

export const StyledDetailInfoBlogImgBox = styled.div`
  width: 100%;
  position: relative;
  padding-top: 100%;
  overflow: hidden;
`;

export const StyledDetailInfoBlogImgTextBox = styled.div`
  position: absolute;
  width: 100%;
  /* padding: 10px 10px; */
  padding: 5px 5px 3px 5px; // 상 우 하 좌
  bottom: 0;
  left: 0;
  background-color:rgb(241, 241, 241, 0.5);
  border-radius: 0 0 8px 8px;
`;

export const StyledDetailInfoBlogImg = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px;
`;

export const StyledDetailTextLink = styled.a`
  outline: none;
  text-decoration: none;
  cursor: pointer;
`;

export const StyledBlogTitleText = styled.p`
  font-size: 1.3rem;
  font-weight: 500;
  color: #0e0e0e;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`

export const StyledBlogDescriptionText = styled.p`
  font-size: 1.2rem;
  color: #0e0e0e;
  height: 3.5rem;
  padding-top: 3px;
  overflow: hidden;
`;

export const StyledNameText = styled.p`

  font-size: 2rem;
  font-weight: 600;
  color: #0e0e0e;
`;

export const StyledActionText = styled.p`
  font-size: 1.4rem;
  font-weight: 600;
  color: #1A9EFE;
`;

export const StyledText = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
  color: #425354;
  white-space: ${({ $whiteSpace }) => $whiteSpace};
`;

export const StyledPriceText = styled.p`
  font-size: 1.6rem;
  font-weight: 600;
  color: #425354;
`;