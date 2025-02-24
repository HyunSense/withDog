import styled from "styled-components";

export const StyledEditBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background-color: #ffffff;
`;

export const StyledEdit = styled.div`
  height: 100%;
  flex: 1 0 0px;
  overflow-y: auto;
  ::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none;
`;

export const StyledTitleAndCountBox = styled.div`
  display: flex;
  align-items: center;
  padding: 10px 16px 10px 16px;
  margin-bottom: 10px;
  gap: 0 1px;
  background-color: #ffffff;
  border-top: 1px solid #eff2f2;
  border-bottom: 1px solid #eff2f2;
`;

export const StyledTitleAndCount = styled.p`
  border: none;
  background: none;
  font-size: 1.4rem;
  font-weight: 500;
`;

export const StyledItemList = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 16px;
  gap: 25px 0;
  /* overflow: scroll;  */
  background-color: #ffffff;
`;

export const StyledRemoveButtonBox = styled.div`
  position: sticky;
  bottom: 0px;
  width: 100%;
  padding: 12px 16px 34px 16px;
  border-top: 1px solid #eff2f2;
  background-color: #ffffff;
`;

export const StyledRemoveButton = styled.button`
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
  border: none;
  border-radius: 8px;
  font-size: 1.5rem;
  font-weight: 600;
  height: 48px;
  color: #ffffff;
  background-color: ${({ $isActive }) => ($isActive ? "#022733" : "#dde2e3")};
  cursor: ${({ $isActive }) => $isActive && "pointer"};
  /* #dde2e3 */
  /* #61d377 */
`;
