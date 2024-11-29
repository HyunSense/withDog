import styled, { createGlobalStyle } from "styled-components";
import SpoqaHanSansNeoRegularWoff2 from "../assets/fonts/SpoqaHanSansNeo-Regular.woff2";
import SpoqaHanSansNeoRegularWoff from "../assets/fonts/SpoqaHanSansNeo-Regular.woff";
import SpoqaHanSansNeoRegularTtf from "../assets/fonts/SpoqaHanSansNeo-Regular.ttf";

import SpoqaHanSansNeoMediumWoff2 from "../assets/fonts/SpoqaHanSansNeo-Medium.woff2";
import SpoqaHanSansNeoMediumWoff from "../assets/fonts/SpoqaHanSansNeo-Medium.woff";
import SpoqaHanSansNeoMediumTtf from "../assets/fonts/SpoqaHanSansNeo-Medium.ttf";

import SpoqaHanSansNeoBoldWoff2 from "../assets/fonts/SpoqaHanSansNeo-Bold.woff2";
import SpoqaHanSansNeoBoldWoff from "../assets/fonts/SpoqaHanSansNeo-Bold.woff";
import SpoqaHanSansNeoBoldTtf from "../assets/fonts/SpoqaHanSansNeo-Bold.ttf";


export const GlobalStyle = createGlobalStyle`

    @font-face {
      font-family: 'Spoqa Han Sans Neo';
      src:  url(${SpoqaHanSansNeoRegularWoff2}) format('woff2'),
            url(${SpoqaHanSansNeoRegularWoff}) format('woff'),
            url(${SpoqaHanSansNeoRegularTtf}) format('ttf');
      font-weight: 400;
      font-display: swap;
    }
    
    @font-face {
      font-family: 'Spoqa Han Sans Neo';
      src:  url(${SpoqaHanSansNeoMediumWoff2}) format('woff2'),
            url(${SpoqaHanSansNeoMediumWoff}) format('woff'),
            url(${SpoqaHanSansNeoMediumTtf}) format('ttf');
      font-weight: 500;
      font-display: swap;
    }

    @font-face {
      font-family: 'Spoqa Han Sans Neo';
      src:  url(${SpoqaHanSansNeoBoldWoff2}) format('woff2'),
            url(${SpoqaHanSansNeoBoldWoff}) format('woff'),
            url(${SpoqaHanSansNeoBoldTtf}) format('ttf');
      font-weight: 600;
      font-display: swap;
    }

  html {

    font-size: 62.5%;// (1rem = 10px)
    font-family: 'Spoqa Han Sans Neo', 'sans-serif';
  }

  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  a {
    outline: none;
    text-decoration: none;
    color: black;

    &:visited {
      color: black;
    }

    &:link {
      color: black;
    }

    &:active {
      color: black;
    }
  }

  input, button, textarea, select {
    font-family: 'Spoqa Han Sans Neo', 'sans-serif'; // 명시적으로 폰트 지정
    font-size: inherit; // 폰트 크기를 상속받게 설정
  }
`;


export const StyledBackground = styled.div`
  position: fixed;
  z-index: -1;
  width: 100vw;
  height: 100%;
  background-size: cover;
  background-repeat: no-repeat;
  background-color: rgb(243, 246, 246);
`;