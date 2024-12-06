import api from "./api";

// GET AllPlaces
export const getAllPlaces = (params) => api.get("/places", { params: params });

// GET Places
export const getPlace = (id) => api.get(`/places/${id}`);

// POST Places
export const postPlaces = (data) =>
  api.post("/places", data, {
    headers: { "Content-Type": "multipart/form-data" },
  });

export const updatePlaces = (placeId, data) =>
  api.put(`/places/${placeId}`, data, {
    headers: { "Content-Type": "multipart/form-data" },
  });

export const deletePlaces = (
  data // post와달리 delete 두번째 인자는 config, delete(url, config)
) =>
  api.delete("/places", {
    // data를 담으려면 config에 data 속성 추가
    data: data,
  });

// GET BookmarkStatus
export const getBookmarkStatus = (placeId) =>
  api.get(`/places/${placeId}/bookmarks/status`);

export const getAllBookmarks = () => api.get("/places/bookmarks");

// POST AddBookmark
export const addBookmark = (placeId) =>
  api.post(`/places/${placeId}/bookmarks`);

// DELETE DeleteBookmark
export const deleteBookmark = (placeId) =>
  api.delete(`/places/${placeId}/bookmarks`);

// DELETE DeleteSelectedBookmarks
export const deleteSelectedBookmarks = (data) =>
  api.delete("/places/bookmarks", { data: data });

// GET Top3Places
export const getTop3Places = (params) =>
  api.get("/places/top3", { params: params });
