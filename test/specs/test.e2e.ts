describe("My Login application", () => {
  it("should reset the frame when the page is reloaded", async () => {
    await expect($("#tinymce")).not.toBePresent();
    await browser.switchFrame($("iframe"));
    await expect($("#tinymce")).toBePresent();
    await browser.refresh();
    await expect($("#tinymce")).not.toBePresent();
    await browser.switchFrame($("iframe"));
    await expect($("#tinymce")).toBePresent();
  });
});
