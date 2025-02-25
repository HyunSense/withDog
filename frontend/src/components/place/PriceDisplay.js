export const PriceDisplay = ({ price }) => {
  return (
    <>
    {price === 0 ? (
      "무료"
    ) : (
      <>
        {price.toLocaleString("ko-KR")}
        <span>원 ~</span>
      </>
    )}
  </>
  );
};
