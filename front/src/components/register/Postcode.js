// import { useDaumPostcodePopup } from "react-daum-postcode";
// import { StyledSearchAddressButton } from "../../styles/Postcode.Styled";

// const Postcode = ({ children, addressChange }) => {
//   const scriptUrl =
//     "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
//   const open = useDaumPostcodePopup(scriptUrl);

//   const handleComplete = (data) => {
//     // console.log("zoneCode = ", data.zonecode);
//     // console.log("sido = ", data.sido);
//     // console.log("sigungu = ", data.sigungu);
//     let sido = data.sido;
//     let sigungu = data.sigungu;
//     let fullAddress = data.address;
//     /* 동, 건물명 포함할경우 */
//     // let extraAddress = "";
//     // if (data.addressType === "R") {
//     //   if (data.bname !== "") {
//     //     extraAddress += data.bname;
//     //   }

//     //   if (data.buildingName !== "") {
//     //     extraAddress +=
//     //       extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
//     //   }

//     //   fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
//     // }

//     // const special = "특별자치";
//     // if (sido.includes(special)) {
//     //   sido = sido.replace(special, "").trim();
//     //   fullAddress = fullAddress.replace(special, "").trim();
//     // }
    
//     const addressPart1 = `${sido} ${sigungu}`;
//     const addressPart2 = fullAddress.replace(addressPart1, "").trim();

//     console.log("addressPart1 = ", addressPart1);
//     console.log("addressPart2 = ", addressPart2);
//     const address = {addressPart1, addressPart2};
//     console.log("address = ", address);
//     addressChange(address);
//   };

//   const handleClick = () => {
//     open({ onComplete: handleComplete });
//   };

//   return (
//     <StyledSearchAddressButton type="button" onClick={handleClick}>
//       {children}
//     </StyledSearchAddressButton>
//   );
// };

// export default Postcode;
