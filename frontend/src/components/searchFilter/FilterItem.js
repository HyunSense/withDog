const FilterItem = ({ label, value, isSelected, onSelect }) => {
  return (
    <div
      className={
        "flex justify-center items-center px-4 py-2 rounded-full border border-solid border-gray-200 cursor-pointer " +
        (isSelected
          ? "bg-neutral-950 text-gray-100"
          : "bg-gray-100 text-neutral-950")
      }
      onClick={() => onSelect(value)}
    >
      <span className="text-sm font-normal">{label}</span>
    </div>
  );
};

export default FilterItem;
