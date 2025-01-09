export const blogsValidation = (blogUrls) => {

    if (blogUrls.some((url) => !(url.includes("http://") || url.includes("https://")))) {

        alert("http:// 또는 https://이 포함되어야 합니다.");
        console.log("blogUrls: ", blogUrls);
        return false;
    }

    return true;
}
