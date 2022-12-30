import constant from "./constant";

async function get(path, params) {
    try {
        const res = await fetch(constant.LOCAL_API_URL + path, {
            method: "GET",
            params: params
        })
        return res;
    } catch (error) {
        console.log(error);
        return null;
    }
}

async function post(path, body) {
    try {
        const res = await fetch(constant.LOCAL_API_URL + path, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body),
        });
        return res;
    } catch (error) {
        console.log(error);
        return null;
    }
}

async function put(path, body) {
    try {
        const res = await fetch(constant.LOCAL_API_URL + path, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body),
        });
        return res;
    } catch (error) {
        console.log(error);
        return null;
    }
}

async function postFormData(path, body) {
    try {
        const formData = new FormData();
        for (let key in body) {
            formData.append(key, body[key]);
        }
        const res = await fetch(constant.LOCAL_API_URL + path, {
            method: "POST",
            body: formData
        })
        return res;
    } catch (error) {
        console.log(error);
        return null;
    }
}
async function postMedia(path, file, fileType) {
    try {
        const formData = new FormData();
        formData.append("file", file);
        formData.append("fileType", fileType);
        const res = await fetch(constant.LOCAL_API_URL + path, {
            method: "POST",
            body: formData
        })
        return res;
    } catch (error) {
        console.log(error);
        return null;
    }
}

async function putMedia(path, file, fileType) {
    try {
        const formData = new FormData();
        formData.append("file", file);
        formData.append("fileType", fileType);
        const res = await fetch(constant.LOCAL_API_URL + path, {
            method: "PUT",
            body: formData
        })
        return res;
    } catch (error) {
        console.log(error);
        return null;
    }
}

async function putFormData(path, body) {
    try {
        const formData = new FormData();
        for (let key in body) {
            formData.append(key, body[key]);
        }
        const res = await fetch(constant.LOCAL_API_URL + path, {
            method: "PUT",
            body: formData
        })
        return res;
    } catch (error) {
        console.log(error);
        return null;
    }
}


async function del(path, body) {
    try {
        const res = await fetch(constant.LOCAL_API_URL + path, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body),
        });
        return res;
    } catch (error) {
        console.log(error);
        return null;
    }
}

export default { get, post, put, postFormData, putFormData, postMedia, putMedia, del };