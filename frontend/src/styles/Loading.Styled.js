import styled from "styled-components";

export const StyledLoadingContainer = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); //화면 중앙에 배치
  min-width: 720px;
  min-height: 100vh; //화면의 최소 높이는 화면 높이로 설정
  z-index: 9999;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: aliceblue;
  pointer-events: auto;
`;

export const StyledText = styled.p`
  margin-top: 10px;
  font-size: 1.5rem;
  font-weight: 500;
`;

// export const StyledLoadingContainer = styled.div
//   position: fixed;
//   min-width: 720px;
//   min-height: calc(var(--vh, 1vh) * 100);
//   z-index: 9999;
//   display: flex;
//   flex-direction: column;
//   justify-content: center;
//   align-items: center;
//   background-color: aliceblue;
//   pointer-events: auto;
// ;
