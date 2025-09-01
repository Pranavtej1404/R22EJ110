"use client";

import { useState } from "react";
import axios from "axios";

interface ShortUrlResponse {
  shortcode: string;
  originalUrl: string;
  expiry: string;
}
// Main Home Component
export default function Home() {
  const [urls, setUrls] = useState<string[]>([""]);
  const [validityMinutes, setValidityMinutes] = useState<number>(30);
  const [customShortcode, setCustomShortcode] = useState<string>("");
  const [results, setResults] = useState<ShortUrlResponse[]>([]);
  const [error, setError] = useState<string>("");

  const handleChange = (index: number, value: string) => {
    const updated = [...urls];
    updated[index] = value;
    setUrls(updated);
  };

  const addField = () => {
    if (urls.length < 5) setUrls([...urls, ""]);
  };

  const handleSubmit = async () => {
    try {
      setError("");
      const urlsToSend = urls.filter((url) => url.trim() !== "");
      if (!urlsToSend.length) {
        setError("Please enter at least one URL.");
        return;
      }

      const newResults: ShortUrlResponse[] = [];
      for (const url of urlsToSend) {
        const payload = { url, validity: validityMinutes, shortcode: customShortcode || undefined };
        const res = await axios.post<ShortUrlResponse>("http://localhost:8080/api/shorten", payload);
        newResults.push(res.data);
      }
      setResults(newResults);
    } catch (err: any) {
      setError(err.response?.data?.message || "Error creating short URL. Try again.");
    }
  };

  return (
    <div className="bg-gray-900 min-h-screen text-white flex flex-col items-center p-6">
      <h1 className="text-3xl font-bold mb-6 text-center text-cyan-400">URL Shortener</h1>

      <div className="bg-gray-800 p-6 rounded-lg w-full max-w-xl mb-6 border border-gray-700">
        <h2 className="text-xl font-semibold mb-4 text-cyan-300">Enter URLs</h2>

        {urls.map((url, idx) => (
          <input
            key={idx}
            type="text"
            placeholder="Enter original URL"
            value={url}
            onChange={(e) => handleChange(idx, e.target.value)}
            className="w-full mb-4 p-3 rounded-md bg-gray-700 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-cyan-500 text-white"
          />
        ))}

        {urls.length < 5 && (
          <button
            onClick={addField}
            className="mb-4 px-4 py-2 rounded-md bg-cyan-600 hover:bg-cyan-500 transition-colors w-full"
          >
            + Add Another URL
          </button>
        )}

        <input
          type="number"
          min={1}
          value={validityMinutes}
          onChange={(e) => setValidityMinutes(Number(e.target.value))}
          placeholder="Validity in minutes"
          className="w-full mb-4 p-3 rounded-md bg-gray-700 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-cyan-500 text-white"
        />

        <input
          type="text"
          value={customShortcode}
          onChange={(e) => setCustomShortcode(e.target.value)}
          placeholder="Custom shortcode (optional)"
          className="w-full mb-4 p-3 rounded-md bg-gray-700 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-cyan-500 text-white"
        />

        <button
          onClick={handleSubmit}
          className="w-full py-3 rounded-md bg-cyan-600 hover:bg-cyan-500 transition-colors text-lg font-semibold"
        >
          Shorten
        </button>

        {error && <p className="text-red-500 mt-4 font-medium">{error}</p>}
      </div>

      {results.length > 0 && (
        <div className="bg-gray-800 p-6 rounded-lg w-full max-w-xl border border-gray-700">
          <h2 className="text-xl font-semibold mb-4 text-cyan-300">Generated Short URLs</h2>
          <ul className="space-y-3">
            {results.map((r, idx) => (
              <li key={idx} className="bg-gray-700 p-3 rounded-md border border-gray-600">
                <a
                  href={`http://localhost:8080/api/${r.shortcode}`}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-cyan-300 hover:underline break-all"
                >
                  {`http://localhost:8080/${r.shortcode}`}
                </a>{" "}
                → <span className="text-gray-300">{r.originalUrl}</span> (expires at {r.expiry})
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}
