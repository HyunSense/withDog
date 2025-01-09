import PrevButton from "../common/PrevButton";
import * as S from "../../styles/BookmarkHeader.Styled";

const BookmarkHeader = () => {
  return (
    <S.StyledBookmarkHeader>
      <S.StyledPrevButtonBox>
        <PrevButton to="/" />
      </S.StyledPrevButtonBox>
      <S.StyledTitleText>북마크</S.StyledTitleText>
      <S.StyledBalanceDummy></S.StyledBalanceDummy>
    </S.StyledBookmarkHeader>
  );
};

export default BookmarkHeader;
