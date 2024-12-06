// import * as S from "../../styles/RegisterMain.Styled";
// import Postcode from "../register/Postcode";
// import { useState } from "react";
// import RegisterImageUpload from "./RegisterImageUpload";
// import { postPlaces } from "../apis/place";

// const RegisterMain = () => {
//   const [selected, setSelected] = useState("camp");
//   const [values, setValues] = useState({
//     // category: "",
//     name: "",
//     phone: "",
//     addressPart1: "",
//     addressPart2: "",
//     price: "",
//     reservationUrl: "",
//     blogUrls: ["", "", "", ""],
//   });
//   const [images, setImages] = useState([]);

//   const handleCategorySelect = (category) => {
//     console.log("category value = ", category);
//     setSelected(category);
//     // setValues((prev) => ({
//     //   ...prev,
//     //   category: category,
//     // }));
//   };

//   const handleTextInputChange = (e) => {
//     //이전값만 가져오기때문에 input 태그에 value={} 를 작성하여 최신상태 value를 관리
//     const { name, value } = e.target;

//     setValues((prev) => ({
//       ...prev,
//       [name]: value,
//     }));

//     // console.log("textInput = ", values);
//   };

//   const handleAddressChange = (address) => {
//     setValues((prev) => ({
//       ...prev,
//       addressPart1: address.addressPart1,
//       addressPart2: address.addressPart2,
//     }));
//   };

//   const handleBlogUrlChange = (e, index) => {
//     const { value } = e.target;
//     const newBlogUrls = [...values.blogUrls];
//     newBlogUrls[index] = value;

//     setValues((prev) => ({
//       ...prev,
//       blogUrls: newBlogUrls,
//     }));
//   };

//   const handleImageChange = (newImages) => {
//     setImages(newImages);
//   };
//   const handleOnSubmit = async (e) => {
//     e.preventDefault();
//     const textFormData = {
//       ...values,
//       category: selected,
//       blogUrls: values.blogUrls.filter((url) => url !== ""),
//     };
//     console.log("placeData = ", textFormData);
//     console.log("images = ", images);

//     const formData = new FormData();
//     images.forEach((image) => formData.append("images", image));

//     // blob으로 담지 않으면 MIME타입이 기본적으로 text/plain으로 간주된다.
//     // blob을 생성하면서 type을 지정하면 서버가 데이터를 json으로 명확히 인식할 수있다.
//     const jsonValues = JSON.stringify(textFormData);
//     const blob = new Blob([jsonValues], { type: "application/json" });
//     console.log("jsonValues = ", jsonValues);
//     console.log("blob = ", blob);
//     formData.append("placeData", blob);

//     // if (
//     //   // buttonValue === "register" &&
//     //   filteredBlogLinks.some(
//     //     (link) => !(link.includes("http://") || link.includes("https://"))
//     //   )
//     // ) {
//     //   alert("http:// 또는 https:// 이 포함 되어야합니다.");
//     //   console.log("e.target.value = ", e.target.value);
//     //   return;
//     // }

//     try {
//       const response = await postPlaces(formData);

//       console.log("RegisterMain response = ", response);
//     } catch (error) {
//       console.error("등록 오류", error);
//     }
//   };

//   return (
//     <S.StyledRegisterMain>
//       <S.StyledRegisterForm onSubmit={handleOnSubmit}>
//         <S.StyledCategoryBox>
//           <S.StyledText>종류</S.StyledText>
//           <S.StyledCategoryButtonBox>
//             <S.StyledCategoryButton
//               type="button"
//               $borderRadius="5px 0px 0px 5px"
//               $isSelected={selected === "camp"}
//               onClick={() => handleCategorySelect("camp")}
//             ><S.StyledText>
//               캠핑
//             </S.StyledText>
//             </S.StyledCategoryButton>
//             <S.StyledCategoryButton
//               type="button"
//               $borderRadius="0px 5px 5px 0px"
//               $borderLeft="none"
//               $isSelected={selected === "park"}
//               onClick={() => handleCategorySelect("park")}
//             ><S.StyledText>
//               공원
//             </S.StyledText>
//             </S.StyledCategoryButton>
//           </S.StyledCategoryButtonBox>
//         </S.StyledCategoryBox>
//         <S.StyledImageUploadBox>
//           <S.StyledText>이미지등록(최대 5개)</S.StyledText>
//           <RegisterImageUpload images={images} onChange={handleImageChange} />
//         </S.StyledImageUploadBox>
//         <S.StyledInputBox>
//           <S.StyledText>이름</S.StyledText>
//           <S.StyledInput
//             type="text"
//             name="name"
//             placeholder="ex) withDog 캠핑장"
//             onChange={handleTextInputChange}
//             value={values.name}
//           />
//         </S.StyledInputBox>
//         <S.StyledInputBox>
//           <S.StyledText>전화번호</S.StyledText>
//           <S.StyledInput
//             type="text"
//             name="phone"
//             placeholder="ex) 010-1234-5678"
//             onChange={handleTextInputChange}
//             value={values.phone}
//           />
//         </S.StyledInputBox>
//         <S.StyledInputBox>
//           <S.StyledText>주소</S.StyledText>
//           <S.StyledAddressInputBox>
//             <S.StyledInput
//               type="text"
//               width="100%"
//               value={`${values.addressPart1} ${values.addressPart2}`.trim()}
//               onChange={handleTextInputChange}
//               readOnly
//             />
//             <Postcode addressChange={handleAddressChange}>검색</Postcode>
//           </S.StyledAddressInputBox>
//         </S.StyledInputBox>
//         <S.StyledInputBox>
//           <S.StyledText>가격</S.StyledText>
//           <S.StyledInput
//             type="number"
//             name="price"
//             placeholder="ex) 50000"
//             onChange={handleTextInputChange}
//           />
//         </S.StyledInputBox>
//         <S.StyledInputBox>
//           <S.StyledText>예약 링크</S.StyledText>
//           <S.StyledInput
//             type="text"
//             name="reservationUrl"
//             placeholder="ex) https://withDog.reservation.com"
//             onChange={handleTextInputChange}
//           />
//         </S.StyledInputBox>
//         <S.StyledInputBox direction="column" $alignItems="flex-start">
//           <S.StyledText>
//             블로그 링크 *http://, https:// 를 반드시 포함해주세요.*
//           </S.StyledText>
//           {values.blogUrls.map((url, index) => (
//             <S.StyledInput
//               width="100%"
//               type="text"
//               key={index}
//               value={url}
//               onChange={(e) => handleBlogUrlChange(e, index)}
//             />
//           ))}
//         </S.StyledInputBox>
//         <S.StyeldRegisterButton
//           type="submit"
//           name="action"
//           value="register"
//           $marginTop="50px"
//         >
//           등록
//         </S.StyeldRegisterButton>
//         <S.StyeldRegisterButton
//           type="submit"
//           name="action"
//           value="delete"
//           $marginTop="10px"
//         >
//           삭제
//         </S.StyeldRegisterButton>
//       </S.StyledRegisterForm>
//     </S.StyledRegisterMain>
//   );
// };

// export default RegisterMain;
