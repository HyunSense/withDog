import React from "react";
import withDogLogoFooter from "../../assets/images/withDog-logo-black.png";
import githubIcon from "../../assets/images/github-icon-80px-black.png";
import notionIcon from "../../assets/images/notion-icon-80px-black.png";
import googleIcon from "../../assets/images/google-icon-80px-black.png";

const footerSnsItems = [
  { icon: githubIcon, url: "https://github.com/HyunSense" },
  {
    icon: notionIcon,
    url: "https://hyunsense.notion.site/Study-cd2cc1f341b44e94886429fc39fd5b4e?pvs=4",
  },
  {
    icon: googleIcon,
    url: "https://drive.google.com/file/d/1D3DteCCg0XqvqKuORpXA-HdEy5dDjEBL/view?usp=sharing",
  },
];

const Footer = () => {
  return (
    <footer className="relative px-4 py-2 bg-slate-50">
      <div className="flex justify-between items-center pb-2">
        <div className="w-16">
          <img src={withDogLogoFooter} alt="푸터 메인 로고" />
        </div>
        <div className="flex items-center gap-x-3">
          {footerSnsItems.map((item) => (
            <a key={item.url} href={item.url} target="_blank" rel="noreferrer">
              <div className="w-5">
                <img className="w-full h-full" src={item.icon} alt="SNS 로고" />
              </div>
            </a>
          ))}
        </div>
      </div>
      <div className="flex flex-col gap-y-1 border-t border-gray-300 py-2">
        <span className="text-xs font-semibold text-zinc-600">
          WithDog Project
        </span>
        <span className="text-xs text-zinc-600">제작자: 현재훈</span>
        <span className="text-xs text-zinc-600">
          주소 : 인천광역시 연수구 워터프런트로 150
        </span>
        <span className="text-xs text-zinc-600">
          이메일 : hyunsense1022@gmail.com
        </span>
        <span className="text-xs text-zinc-600">전화번호 : 010-4394-0571</span>
        <span className="text-xs font-semibold text-zinc-600">
          본 사이트는 개인 포트폴리오 웹 페이지 입니다.
        </span>
        <div className="flex flex-col gap-y-0.5 text-xs text-zinc-600">
          <span className="underline">
            사이트 내 이미지 및 정보는 출처에서 제공된 자료를 기반으로 하며,
            상업적 목적이 아닌 개인 프로젝트 용도로 사용되었습니다.
          </span>
          <span>이미지 출처:</span>
          <span>- 네이버 지도(map.naver.com)</span>
          <span>- 캠핏(camfit.co.kr)</span>
          <span>
            저작권 관련 문의가 있을 경우, 연락 주시면 즉시 조치하겠습니다.
          </span>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
