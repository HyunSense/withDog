import styled from "styled-components";
import AdminEditItem from "./AdminEditItem";
import { useCallback, useEffect, useRef, useState } from "react";
import { deletePlaces, getAllPlaces } from "../apis/place";
import { useNavigate } from "react-router-dom";

const StyledEditBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background-color: #ffffff;
`;

const StyledEdit = styled.div`
  height: 100%;
  flex: 1 0 0px;
  /* overflow: scroll; */
  overflow-y: auto;
  ::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none;
`;

const StyledCategoryBox = styled.div`
  /* position: sticky; */
  /* top: 48px; */
  /* z-index: 1000; */
  display: flex;
  align-items: center;
  padding: 15px 16px 15px 16px;
  /* padding: 20px 10px 20px 10px; */
  margin-bottom: 10px;
  gap: 0 1px;
  background-color: #ffffff;
  border-top: 1px solid #eff2f2;
  border-bottom: 1px solid #eff2f2;
`;

const StyledCategoryButton = styled.button`
  cursor: pointer;
  border: none;
  background: none;
  font-size: 1.4rem;
  font-weight: 500;
  border-radius: 8px;
  color: ${({$isActive}) => ($isActive ? "#ffffff" : "#000000")};
  background-color: ${({$isActive}) => ($isActive && "#022733")};
  padding: 1px 7px;
`;

const StyledItemList = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 16px;
  gap: 25px 0;
  /* overflow: scroll;  */
  background-color: #ffffff;
`;

const StyledRemoveButtonBox = styled.div`
  position: sticky;
  bottom: 0px;
  width: 100%;
  padding: 12px 16px 34px 16px;
  border-top: 1px solid #eff2f2;
  background-color: #ffffff;
`;

const StyledRemoveButton = styled.button`
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

const AdminEdit = () => {
  const [places, setPlaces] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);
  const [selectCategory, setSelectCategory] = useState(null);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const loadMoreRef = useRef(null);

  const navigate = useNavigate();
  const handleCategoryClick = (category) => {
    console.log("category = ", category);
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
    console.log("checkBox id = ", id);
    setSelectedItems((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    );
  };

  const handleDelete = () => {
    if (selectedItems.length === 0) {
      return;
    }

    console.log("selectedItems = ", selectedItems);
    fetchDeletePlaces(selectedItems);
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

    if (loadMoreRef.current) {
      observer.observe(loadMoreRef.current);
    }

    return () => {
      if (loadMoreRef.current) {
        observer.unobserve(loadMoreRef.current);
      }
    };
  }, [onIntersection]);

  const fetchDeletePlaces = async (ids) => {
    try {
      console.log("ids = ", ids);
      const response = await deletePlaces({ids: ids});
      console.log("fetchDeletePlaces response = ", response);
    } catch (error) {
      console.error("fetchDeletePalces error = ", error);
    }

    setPlaces((prev) => prev.filter((place) => !ids.includes(place.id)));
    setSelectedItems([]);
  };

  const fetchMorePlaces = async (page, category) => {
    try {
      console.log("fetchMorePlaces Category = ", category);
      const response = await getAllPlaces({ page: page, category: category });
      console.log("adminEdit response = ", response);
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
      <StyledEditBox>
        <StyledEdit>
          <StyledCategoryBox>
            <StyledCategoryButton $isActive={selectCategory === null} onClick={() => handleCategoryClick(null)}>
              전체
            </StyledCategoryButton>
            <StyledCategoryButton $isActive={selectCategory === "camp"} onClick={() => handleCategoryClick("camp")}>
              캠핑
            </StyledCategoryButton>
            <StyledCategoryButton $isActive={selectCategory === "park"} onClick={() => handleCategoryClick("park")}>
              공원
            </StyledCategoryButton>
          </StyledCategoryBox>
          <StyledItemList>
            {places.map((place) => (
              <AdminEditItem
                key={place.id}
                item={place}
                checked={selectedItems.includes(place.id)}
                onChange={() => handleCheckboxChange(place.id)}
              />
            ))}
          </StyledItemList>
          {hasMore && <div ref={loadMoreRef}></div>}
        </StyledEdit>
      </StyledEditBox>
      <StyledRemoveButtonBox>
        <StyledRemoveButton
          onClick={() => handleDelete()}
          $isActive={selectedItems.length > 0}
          disabled={selectedItems.length === 0}
        >
          장소 삭제
        </StyledRemoveButton>
      </StyledRemoveButtonBox>
    </>
  );
};

export default AdminEdit;
