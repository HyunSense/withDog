export const PriceDisplay = ({ price }) => {
  return (
    <>
      {price.toLocaleString("ko-KR")}
      <span>원 ~</span>
    </>
  );
};
