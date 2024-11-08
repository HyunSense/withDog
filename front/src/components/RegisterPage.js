import React, { useState } from "react";
import styled from "styled-components";
import Header from "./Header";
import StyledText from "./StyledText";
import addIcon from "../images/add.png";
import removeIcon from "../images/remove.png";
import upDownIcon from "../images/up-down.png";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const StyledRegisterContainer = styled.div`
  height: 100vh;
`;

const StyledRegisterMain = styled.main`
  display: flex;
  /* padding: 0 70px; */
  margin-top: 50px;
  justify-content: center;
`;

const StyledRegisterForm = styled.form`
  display: flex;
  flex-direction: column;
  width: 70%;
`;

const StyledCategoryBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const StyledRadioButtonBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const StyledRadioButton = styled.input`
  display: none;
`;

const StyledRadioLabel = styled.label`
  font-size: 1.5rem;
  padding: 3px 10px;
  cursor: pointer;
  border: ${({ $border }) => $border};
  border-radius: ${({ $borderRadius }) => $borderRadius};
  border-left: ${({ $borderLeft }) => $borderLeft};

  background-color: ${({ selected }) => (selected ? "#000000" : "ffffff")};
  color: ${({ selected }) => (selected ? "#ffffff" : "#000000")};
`;

const StyledImageRegisterBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 25px;
`;

const StyledImageListBox = styled.div`
  display: flex;
  flex-direction: column;
  height: 130px;
  margin-top: 8px;
  border: 0.4px solid #d9d9d9;
  border-radius: 8px;
  resize: none;
  padding: 5px 7px;
  overflow-y: auto;
`;

const StyledImageItem = styled.div`
  padding: 1px;
  cursor: pointer;
  font-size: 1.5rem;
  background-color: ${({ selected }) => (selected ? "#e0e0e0" : "#ffffff")};

  /* &:hover {
    background-color: #e0e0e0;
  } */
`;

const StyledRegisterControlBox = styled.div`
  margin-top: 10px;
  margin-bottom: 20px;
  display: flex;
  justify-content: end;
  align-items: center;
  gap: 8px;
`;

const StyledRegisterControlIcon = styled.img`
  width: ${({ width }) => width || "25px"};
  height: ${({ height }) => height || "25px"};
  margin: ${({ margin }) => margin};
  rotate: ${({ rotate }) => rotate};
  cursor: pointer;
`;

const StyledFileInput = styled.input`
  display: none;
`;

const StyledRegisterInfo = styled.div`
  display: flex;
  justify-content: space-between;
  /* align-items: center; */
  margin-top: 5px;
  flex-direction: ${({ direction }) => direction || "row"};
  align-items: ${({ $alignItems }) => $alignItems || "center"};
  gap: 5px;
`;

const StyledRegisterInfoInput = styled.input`
  width: 65%;
  width: ${({ width }) => width || "65%"};
  height: 25px;
  border: 0.4px solid #d9d9d9;
  border-radius: 5px;
  font-size: 1.5rem;
  padding: 0 8px;

  &:focus {
    outline: none;
  }
`;

const StyledSearchAddressButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  border: 0.4px solid #d9d9d9;
  border-radius: 5px;
  background-color: #ffffff;
  width: 60px;
  height: 25px;
`;

const StyledSearchAddressWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 65%;
  gap: 5px;
`;

const StyeldRegisterButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2rem;
  font-weight: 700;
  border: 0.4px solid #d9d9d9;
  border-radius: 5px;
  background-color: #ffffff;
  margin-top: ${({ $marginTop }) => $marginTop};
  height: 40px;
  cursor: pointer;
`;

function RegisterPage() {
  const [selectedOption, setSelectedOption] = useState("camp");

  const handleOptionChange = (e) => {
    setSelectedOption(e.target.value);
  };

  const [images, setImages] = useState([]);
  const [selectedIndex, setSelectedIndex] = useState(null);

  const handleAddImage = (e) => {
    const image = e.target.files[0];

    if (image) {
      // setImages([...images, file.name]);
      setImages([...images, image]);
    }
  };

  const handleRemoveImage = () => {
    if (selectedIndex !== null) {
      const updatedImages = images.filter(
        (_, index) => index !== selectedIndex
      );
      setImages(updatedImages);
      setSelectedIndex(null);
    }
  };

  const handleSelectImage = (index) => {
    setSelectedIndex(index);
  };

  const handleMoveUp = () => {
    if (selectedIndex !== null && selectedIndex > 0) {
      // 첫 번째가 아닌 경우에만
      const newImages = [...images];
      [newImages[selectedIndex - 1], newImages[selectedIndex]] = [
        newImages[selectedIndex],
        newImages[selectedIndex - 1],
      ];
      setImages(newImages);
      setSelectedIndex(selectedIndex - 1);
    }
  };

  const handleMoveDown = () => {
    if (selectedIndex !== null && selectedIndex < images.length - 1) {
      const newImages = [...images];
      [newImages[selectedIndex + 1], newImages[selectedIndex]] = [
        newImages[selectedIndex],
        newImages[selectedIndex + 1],
      ];
      setImages(newImages);
      setSelectedIndex(selectedIndex + 1);
    }
  };

  const [blogLinks, setBlogLinks] = useState(["", "", "", ""]);

  const handleBlogLinkChange = (i, e) => {
    const newBlogLinks = [...blogLinks];
    newBlogLinks[i] = e.target.value;
    setBlogLinks(newBlogLinks);
  };

  const [values, setValues] = useState({
    name: "",
    phone: "",
    address: "",
    price: "",
    reservationLink: "",
  });

  const { name, phone, address, price, reservationLink } = values;

  const handleOnChange = (e) => {
    const { name, value, type } = e.target;

    if (type === "radio") {
      setSelectedOption(value);
    } else {
      setValues({
        ...values,
        [name]: value,
      });
    }
  };

  const handleOnSubmit = async (e) => {
    e.preventDefault();
    const buttonValue = e.target.value;
    const dummyImageUrls = ["test1", "test2", "test3", "test4"];

    const filteredBlogLinks = blogLinks.filter((link) => link !== "");

    const formData = {
      ...values,
      categoryName: selectedOption,
      blogUrls: filteredBlogLinks,
      imageUrls: dummyImageUrls,
    };

    console.log(formData);

    if (
      // buttonValue === "register" &&
      filteredBlogLinks.some(
        (link) => !(link.includes("http://") || link.includes("https://"))
      )
    ) {
      alert("http:// 또는 https:// 이 포함 되어야합니다.");
      console.log("e.target.value = ", e.target.value);
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/places",
        formData
      );

      console.log("response = ", response);
    } catch (error) {
      console.error("등록 오류", error);
    }
  };

  return (
    <StyledRegisterContainer>
      <Header /> {/*등록하기, 로그아웃 필요*/}
      <StyledRegisterMain>
        <StyledRegisterForm onSubmit={handleOnSubmit}>
          <StyledCategoryBox>
            <StyledText fontSize="1.5rem">종류</StyledText>
            <StyledRadioButtonBox>
              <StyledRadioButton
                type="radio"
                id="camp"
                name="category"
                value="camp"
                checked={selectedOption === "camp"}
                // onChange={handleOptionChange}
                onChange={handleOnChange}
              />
              <StyledRadioLabel
                htmlFor="camp"
                $border="0.4px solid #d9d9d9"
                $borderRadius="8px 0px 0px 8px"
                selected={selectedOption === "camp"}
              >
                캠핑
              </StyledRadioLabel>
              <StyledRadioButton
                type="radio"
                id="park"
                name="category"
                value="park"
                checked={selectedOption === "park"}
                // onChange={handleOptionChange}
                onChange={handleOnChange}
              />
              <StyledRadioLabel
                htmlFor="park"
                $border="0.4px solid #d9d9d9"
                $borderRadius="0px 8px 8px 0px"
                $borderLeft="none"
                selected={selectedOption === "park"}
              >
                공원
              </StyledRadioLabel>
            </StyledRadioButtonBox>
          </StyledCategoryBox>
          <StyledImageRegisterBox>
            <StyledText fontSize="1.5rem">이미지등록(최대 5개)</StyledText>
            <StyledImageListBox readOnly>
              {images.map((image, index) => (
                <StyledImageItem
                  key={index}
                  selected={index === selectedIndex}
                  onClick={() => handleSelectImage(index)}
                >
                  {image.name}
                </StyledImageItem>
              ))}
            </StyledImageListBox>
            <StyledFileInput
              type="file"
              accept=".jpg ,.png"
              onChange={handleAddImage}
              id="file-input"
            />
            <StyledRegisterControlBox>
              <StyledRegisterControlIcon
                src={upDownIcon}
                rotate="180deg"
                onClick={handleMoveUp}
              />
              <StyledRegisterControlIcon
                src={upDownIcon}
                onClick={handleMoveDown}
              />
              <StyledRegisterControlIcon
                src={addIcon}
                width="23px"
                height="23px"
                // onClick={handleAddImage}
                onClick={() => document.getElementById("file-input").click()}
              />
              <StyledRegisterControlIcon
                src={removeIcon}
                width="23px"
                height="23px"
                onClick={handleRemoveImage}
              />
            </StyledRegisterControlBox>
          </StyledImageRegisterBox>
          <StyledRegisterInfo>
            <StyledText fontSize="1.5rem">이름</StyledText>
            <StyledRegisterInfoInput
              type="text"
              name="name"
              value={name}
              onChange={handleOnChange}
            />
          </StyledRegisterInfo>
          <StyledRegisterInfo>
            <StyledText fontSize="1.5rem">전화번호</StyledText>
            <StyledRegisterInfoInput
              type="text"
              name="phone"
              value={phone}
              onChange={handleOnChange}
            />
          </StyledRegisterInfo>
          <StyledRegisterInfo>
            <StyledText fontSize="1.5rem">주소</StyledText>
            <StyledSearchAddressWrapper>
              <StyledRegisterInfoInput
                width="80%"
                type="text"
                name="address"
                value={address}
                onChange={handleOnChange}
              ></StyledRegisterInfoInput>
              <StyledSearchAddressButton type="button">
                검색
              </StyledSearchAddressButton>
            </StyledSearchAddressWrapper>
          </StyledRegisterInfo>
          <StyledRegisterInfo>
            <StyledText fontSize="1.5rem">가격</StyledText>
            <StyledRegisterInfoInput
              type="text"
              name="price"
              value={price}
              onChange={handleOnChange}
            />
          </StyledRegisterInfo>
          <StyledRegisterInfo>
            <StyledText fontSize="1.5rem">예약 링크</StyledText>
            <StyledRegisterInfoInput
              type="text"
              name="reservationLink"
              value={reservationLink}
              onChange={handleOnChange}
            />
          </StyledRegisterInfo>
          <StyledRegisterInfo direction="column" $alignItems="flex-start">
            <StyledText fontSize="1.5rem">
              블로그 링크 *http://, https:// 를 반드시 포함해주세요.*
            </StyledText>
            {blogLinks.map((link, index) => (
              <StyledRegisterInfoInput
                key={index}
                width="100%"
                type="text"
                value={link}
                onChange={(e) => handleBlogLinkChange(index, e)}
              />
            ))}
          </StyledRegisterInfo>
          <StyeldRegisterButton
            type="submit"
            name="action"
            value="register"
            $marginTop="50px"
          >
            등록
          </StyeldRegisterButton>
          <StyeldRegisterButton
            type="submit"
            name="action"
            value="delete"
            $marginTop="10px"
          >
            삭제
          </StyeldRegisterButton>
        </StyledRegisterForm>
      </StyledRegisterMain>
    </StyledRegisterContainer>
  );
}

export default RegisterPage;
