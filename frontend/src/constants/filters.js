export const FILTER_OPTIONS = [
    {
      id: 'types',
      title: '장소 유형',
      options: [
        { label: '캠핑', value: 'camp' },
        { label: '공원', value: 'park' },
        { label: '펜션', value: 'pension' },
        { label: '카페', value: 'cafe' },
        { label: '음식점', value: 'restaurant' },
      ],
      multiSelect: true,
    },
    {
      id: 'city',
      title: '지역',
      options: [
        { label: '서울', value: '서울' },
        { label: '부산', value: '부산' },
        { label: '대구', value: '대구' },
        { label: '인천', value: '인천' },
        { label: '광주', value: '광주' },
        { label: '대전', value: '대전' },
        { label: '울산', value: '울산' },
        { label: '세종', value: '세종' },
        { label: '경기', value: '경기' },
        { label: '강원', value: '강원' },
        { label: '충북', value: '충북' },
        { label: '충남', value: '충남' },
        { label: '전북', value: '전북' },
        { label: '전남', value: '전남' },
        { label: '경북', value: '경북' },
        { label: '경남', value: '경남' },
        { label: '제주', value: '제주' },

      ],
      multiSelect: true,
    },
    {
      id: 'petAccessTypes',
      title: '반려견 이용 유형',
      options: [
        { label: '반려견 동반', value: 'petFriendly' },
        { label: '반려견 전용', value: 'petOnly' },
      ],
      multiSelect: true,
    },
    {
      id: 'petSizes',
      title: '허용견 크기',
      options: [
        { label: '소형견', value: 'sm' },
        { label: '중형견', value: 'md' },
        { label: '대형견', value: 'lg' },
      ],
      multiSelect: true,
    },
    {
      id: 'services',
      title: '편의 시설',
      options: [
        { label: '전용 놀이터', value: 'playground' },
        { label: '주차장', value: 'parking' },
        { label: '야외 시설 보유', value: 'outdoorFacilities' },
      ],
      multiSelect: true,
    },
  ];