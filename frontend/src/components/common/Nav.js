import React from "react";
import camp from "../../assets/images/camp-200x200.png";
import park from "../../assets/images/park-200x200.png";
import pension from "../../assets/images/pension-200x200.png";
import cafe from "../../assets/images/cafe-200x200.png";
import restaurant from "../../assets/images/restaurant-200x200.png";
import { Link } from "react-router-dom";

const navItems = [
  { type: "camp", icon: camp, label: "캠핑" },
  { type: "park", icon: park, label: "공원" },
  { type: "pension", icon: pension, label: "펜션" },
  { type: "cafe", icon: cafe, label: "카페" },
  { type: "restaurant", icon: restaurant, label: "음식점" },
];

const Nav = () => {
  return (
    <nav className="flex flex-wrap justify-center items-center gap-3 px-4 pt-2 mb-7">
      {navItems.map((item) => (
        <Link
          to={`/search/result?types=${item.type}`}
          key={item.type}
          className="flex flex-col justify-center items-center cursor-pointer gap-1"
        >
          <div className="flex justify-center items-center w-14 h-14">
            <img
              className="w-full h-full"
              src={item.icon}
              alt={`${item.label} 아이콘`}
            />
          </div>
          <span className="text-sm font-medium text-neutral-950">
            {item.label}
          </span>
        </Link>
      ))}
    </nav>
  );
};

export default Nav;
