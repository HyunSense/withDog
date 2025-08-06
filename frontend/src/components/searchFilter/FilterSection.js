const FilterSection = ({ title, children }) => {
  return (
    <div className="mt-6">
      <p className="flex text-sm font-medium text-neutral-950">{title}</p>
      <div className="flex flex-wrap gap-2 mt-2">{children}</div>
    </div>
  );
};

export default FilterSection;
