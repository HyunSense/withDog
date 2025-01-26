import AdminEditItem from "./AdminEditItem";
import { useCallback, useEffect, useRef, useState } from "react";
import { deletePlaces, getAllPlaces } from "../../apis/place";
import { useNavigate } from "react-router-dom";
import * as S from "../../styles/AdminEdit.Styled";
import { CATEGORY_MAP } from "../../constants/categoryMap";

const AdminEdit = () => {
  const [places, setPlaces] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);
  const [selectCategory, setSelectCategory] = useState(null);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const loadMoreRef = useRef(null);

  const navigate = useNavigate();
  const handleCategoryClick = (category) => {
    setSelectCategory(category);

    setPage(0);
    setHasMore(true);
    setPlaces([]);

    if (category) {
      navigate(`?category=${category}`);
    } else {
      navigate();
    }
  };

  const handleCheckboxChange = (id) => {
    setSelectedItems((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    );
  };

  const handleDelete = () => {
    if (selectedItems.length === 0) {
      return;
    }

    const isConfirmed = window.confirm(`${selectedItems.length}개의 장소를 삭제 하시겠습니까?`);

    if (isConfirmed) {
      fetchDeletePlaces(selectedItems);
    } else {
      console.log("삭제취소");
    }
  };

  const onIntersection = useCallback(
    (entries) => {
      const firstEntity = entries[0];
      if (firstEntity.isIntersecting && hasMore) {
        fetchMorePlaces(page, selectCategory);
      }
    },
    [hasMore, page, selectCategory]
  );

  useEffect(() => {
    const observer = new IntersectionObserver(onIntersection, { threshold: 1 });

    const currentRef = loadMoreRef.current;

    if (currentRef) {
      observer.observe(currentRef);
    }

    return () => {
      if (currentRef) {
        observer.unobserve(currentRef);
      }
    };
  }, [onIntersection]);

  const fetchDeletePlaces = async (ids) => {
    try {
      await deletePlaces({ids: ids});
    } catch (error) {
      console.error("fetchDeletePalces error = ", error);
    }

    setPlaces((prev) => prev.filter((place) => !ids.includes(place.id)));
    setSelectedItems([]);
  };

  const fetchMorePlaces = async (page, category) => {
    try {
      const categoryId = CATEGORY_MAP[category] ?? 0;
      const response = await getAllPlaces({ page: page, categoryId: categoryId });
      const apiResponse = response.data;
      const slice = apiResponse.data;

      setPlaces((prev) => [...prev, ...slice.content]);

      if (slice.last) {
        setHasMore(false);
      } else {
        setPage((prev) => prev + 1);
      }
    } catch (error) {
      console.error("AdminEdit fetchMorePlaces error = ", error);
    }
  };

  return (
    <>
      <S.StyledEditBox>
        <S.StyledEdit>
          <S.StyledCategoryBox>
            <S.StyledCategoryButton $isActive={selectCategory === null} onClick={() => handleCategoryClick(null)}>
              전체
            </S.StyledCategoryButton>
            <S.StyledCategoryButton $isActive={selectCategory === "camp"} onClick={() => handleCategoryClick("camp")}>
              캠핑
            </S.StyledCategoryButton>
            <S.StyledCategoryButton $isActive={selectCategory === "park"} onClick={() => handleCategoryClick("park")}>
              공원
            </S.StyledCategoryButton>
          </S.StyledCategoryBox>
          <S.StyledItemList>
            {places.map((place) => (
              <AdminEditItem
                key={place.id}
                item={place}
                checked={selectedItems.includes(place.id)}
                onChange={() => handleCheckboxChange(place.id)}
              />
            ))}
          </S.StyledItemList>
          {hasMore && <div ref={loadMoreRef}></div>}
        </S.StyledEdit>
      </S.StyledEditBox>
      <S.StyledRemoveButtonBox>
        <S.StyledRemoveButton
          onClick={() => handleDelete()}
          $isActive={selectedItems.length > 0}
          disabled={selectedItems.length === 0}
        >
          장소 삭제
        </S.StyledRemoveButton>
      </S.StyledRemoveButtonBox>
    </>
  );
};

export default AdminEdit;
